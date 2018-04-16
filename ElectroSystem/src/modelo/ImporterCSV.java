package modelo;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import com.csvreader.CsvReader;

import dto.MarcaDTO;
import dto.PiezaDTO;
import dto.PrecioPiezaDTO;
import presentacion.vista.VistaPrincipal;

public class ImporterCSV {

	private Modelo modelo;
	private List<MarcaDTO> marcasBD;
	private List<MarcaDTO> marcasBDProveedor;
	private List<PiezaDTO> piezasBD;
	private int idProveedor;
	private String path;
	private VistaPrincipal parent;

	private final static Integer OK = 1;
	private final static Integer CHANGE_DESCRIPTION = 2;
	private final static Integer CREATE_MARCA = 3;
	private final static Integer CREATE_PIEZA = 4;
	private final static Integer CHANGE_MARCA = 5;
	private final static Integer NO_ASIGNED_MARCA = 6;

	public ImporterCSV(VistaPrincipal parent, Modelo modelo, int idProveedor, String path) {
		this.modelo = modelo;
		this.idProveedor = idProveedor;
		this.path = path;
		this.parent = parent;
	}

	public void importPrices() throws Exception {

		try {
			marcasBD = new ArrayList<MarcaDTO>();
			piezasBD = modelo.obtenerItems();
			List<MarcaDTO> allMarcas = modelo.obtenerMarcas();
			marcasBDProveedor = modelo.obtenerMarcasProveedor(idProveedor);
			for (MarcaDTO marcaDTO : allMarcas) {
				boolean exist = false;
				for (MarcaDTO marcaDTOProveedor : marcasBDProveedor) {
					if (marcaDTO.getIdMarca() == marcaDTOProveedor.getIdMarca()) {
						exist = true;
						break;
					}
				}
				if (!exist)
					marcasBD.add(marcaDTO);
			}

			List<PrecioPiezaDTO> piezasImport = new ArrayList<PrecioPiezaDTO>();
			List<ImportPrice> piezasImport2 = new ArrayList<ImportPrice>();

			CsvReader piezasProveedores_import = new CsvReader(path);
			piezasProveedores_import.readHeaders();
			while (piezasProveedores_import.readRecord()) {
				String nro_serie = piezasProveedores_import.get("nro_serie");
				String descripcion = piezasProveedores_import.get("descripcion");
				String marca = piezasProveedores_import.get("marca");
				String precio_compra = piezasProveedores_import.get("precio");

				if (nro_serie.isEmpty() || marca.isEmpty() || descripcion.isEmpty() || (precio_compra.isEmpty()))
					throw new Exception("El archivo no esta bien formado");
				try {
					new Float(precio_compra);
				} catch (Exception e) {
					throw new Exception("El archivo no esta bien formado. El precio no contiene un formato valido");
				}

				for (PiezaDTO piezaDTO : piezasBD) {
					Integer value = validateParameters(nro_serie, descripcion, marca, precio_compra, piezaDTO);
					if (value.equals(OK))
						piezasImport.add(new PrecioPiezaDTO(piezaDTO, new Float(precio_compra)));
					else {
						piezasImport2.add(new ImportPrice(nro_serie, descripcion, marca, precio_compra, piezaDTO, value));
					}
				}
			}
			piezasProveedores_import.close();

			for (ImportPrice importPrice : piezasImport2) {
				Integer answer = importPrice.getAnswer();
				PiezaDTO pieza = importPrice.getPieza();
				String datosPieza = helpers.Validador.contactenarStrings(pieza.getIdProdPieza() + "", pieza.getDescripcion(), pieza.getMarca().getNombre());

				if (CHANGE_DESCRIPTION.equals(answer)) {
					changeDescription(piezasImport, importPrice, pieza, datosPieza);
				} else if (CREATE_MARCA.equals(answer)) {
					// TODO crear marca y asignarla al proveedor
					createMarca(piezasImport, importPrice, pieza, datosPieza);
				} else if (CHANGE_MARCA.equals(answer)) {
					changeMarca(piezasImport, importPrice, pieza, datosPieza);
				} else if (NO_ASIGNED_MARCA.equals(answer)) {
					asignedMarca(piezasImport, importPrice, pieza, datosPieza);
				} else if (CREATE_PIEZA.equals(answer)) {
					String descripcion = importPrice.getDescripcion();
					String marca = importPrice.getMarca();
					String nombreMarca = pieza.getMarca().getNombre();
					if (descripcion.equals(pieza.getDescripcion())) {
						if (marca.equals(nombreMarca))
							piezasImport.add(new PrecioPiezaDTO(pieza, new Float(importPrice.getPrecio_compra())));
						else {
							for (MarcaDTO marcaDTO : marcasBDProveedor) {
								if (marcaDTO.equals(nombreMarca))
									changeMarca(piezasImport, importPrice, pieza, datosPieza);
							}
							for (MarcaDTO marcaDTO : marcasBD) {
								if (marcaDTO.equals(nombreMarca)) {
									asignedMarca(piezasImport, importPrice, pieza, datosPieza);
									break;
								}
							}
							createMarca(piezasImport, importPrice, pieza, datosPieza);
						}
					} else {
						if (marca.equals(nombreMarca))
							changeDescription(piezasImport, importPrice, pieza, datosPieza);
						else {
							// TODO PREGUNTAR crear pieza ??
						}
					}
				}

			}

			modelo.updatePrecioPiezaProveedor(idProveedor, piezasImport);

		} catch (FileNotFoundException e) {
			e.printStackTrace();
			throw new Exception("No se ha encontrado el archivo especificado.");

		} catch (IOException e) {
			e.printStackTrace();
			throw new Exception("Hubo problemas con el archivo");
		}

	}

	private void asignedMarca(List<PrecioPiezaDTO> piezasImport, ImportPrice importPrice, PiezaDTO pieza, String datosPieza) throws Exception {
		for (MarcaDTO marcaDTO : marcasBD) {
			if (marcaDTO.equals(importPrice.getMarca())) {
				modelo.insertarMarcaProveedor(idProveedor, marcaDTO.getIdMarca());
				asignarMarca(piezasImport, importPrice, pieza, datosPieza, marcaDTO);
				break;
			}
		}
	}

	private void changeDescription(List<PrecioPiezaDTO> piezasImport, ImportPrice importPrice, PiezaDTO pieza, String datosPieza) {
		int respuesta = JOptionPane.showConfirmDialog(parent, "Desea cambiar la descripcion de la pieza: " + datosPieza + "\n" + "Por la siguiente descripcion: " + importPrice.getDescripcion(), null, JOptionPane.YES_NO_OPTION);
		if (respuesta == JOptionPane.YES_OPTION) {
			piezasImport.add(new PrecioPiezaDTO(pieza, new Float(importPrice.getPrecio_compra())));
		} else {
			// TODO NO Importa precio
		}
	}

	private void changeMarca(List<PrecioPiezaDTO> piezasImport, ImportPrice importPrice, PiezaDTO pieza, String datosPieza) {
		MarcaDTO marcaAsignar = null;
		for (MarcaDTO marca : marcasBDProveedor) {
			if (importPrice.getMarca().equals(marca.getNombre())) {
				marcaAsignar = marca;
				break;
			}
		}
		asignarMarca(piezasImport, importPrice, pieza, datosPieza, marcaAsignar);
	}

	private void createMarca(List<PrecioPiezaDTO> piezasImport, ImportPrice importPrice, PiezaDTO pieza, String datosPieza) throws Exception {
		int respuesta = JOptionPane.showConfirmDialog(parent, "Se encontro una marca que no se encuntra en la base. Desea crear la marca y asociarla al proveedor?", null, JOptionPane.YES_NO_OPTION);
		if (respuesta == JOptionPane.YES_OPTION) {

			MarcaDTO marca = new MarcaDTO(0, importPrice.getMarca());
			modelo.agregarMarca(marca);
			modelo.insertarMarcaProveedor(idProveedor, marca.getIdMarca());

			asignarMarca(piezasImport, importPrice, pieza, datosPieza, marca);
		}
	}

	private void asignarMarca(List<PrecioPiezaDTO> piezasImport, ImportPrice importPrice, PiezaDTO pieza, String datosPieza, MarcaDTO marca) {
		int respuesta;
		respuesta = JOptionPane.showConfirmDialog(parent, "Desea cambiar la marca de la pieza: " + datosPieza + "\n" + "Por la siguiente marca: " + importPrice.getMarca(), null, JOptionPane.YES_NO_OPTION);

		if (respuesta == 1) {
			pieza.setMarca(marca);
			piezasImport.add(new PrecioPiezaDTO(pieza, new Float(importPrice.getPrecio_compra())));
		}
	}

	private Integer validateParameters(String nro_serie, String descripcion, String marca, String precio_compra, PiezaDTO pieza) {

		String nombreMarca = pieza.getMarca().getNombre();

		if (nro_serie.equals((pieza.getIdProdPieza() + ""))) {
			if (descripcion.equals(pieza.getDescripcion())) {
				if (marca.equals(nombreMarca))
					return OK;
				else {
					for (MarcaDTO marcaDTO : marcasBDProveedor) {
						if (marcaDTO.equals(nombreMarca))
							return CHANGE_MARCA;
					}
					for (MarcaDTO marcaDTO : marcasBD) {
						if (marcaDTO.equals(nombreMarca))
							return NO_ASIGNED_MARCA;
					}
					return CREATE_MARCA;// creo marca,modifico pieza ??con precio
				}
			} else {
				if (marca.equals(nombreMarca))
					return CHANGE_DESCRIPTION;// pregunto si cambia modelo: si ? modifico precio : cancelar
				else {
					// TODO PREGUNTAR crear pieza ??
				}
			}
		}
		return CREATE_PIEZA;
	}

}
