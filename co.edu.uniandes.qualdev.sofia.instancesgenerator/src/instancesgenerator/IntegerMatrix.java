package instancesgenerator;


public class IntegerMatrix {

	// ------------------------------------------------------
	// Attributes
	// ------------------------------------------------------
	
	/**
	 * Height of the matrix that contains the representation codes.
	 */
	private Integer matrixHeight;
	
	/**
	 * Width of the matrix that contains the representation codes.
	 */
	private Integer matrixWidth;
	
	/**
	 * Integer matrix.
	 */
	private Integer[][] matrix;
	
	// ------------------------------------------------------
	// Constructor
	// ------------------------------------------------------
	
	/**
	 * Constructor.
	 */
	public IntegerMatrix(){
		
	}

	// ------------------------------------------------------
	// Methods
	// ------------------------------------------------------

	public Integer[][] getMatrix() {
		return matrix;
	}

	public void setMatrix(Integer[][] matrix) {
		this.matrix = matrix;
	}

	public Integer getMatrixHeight() {
		return matrixHeight;
	}

	public void setMatrixHeight(Integer matrixHeight) {
		this.matrixHeight = matrixHeight;
	}

	public Integer getMatrixWidth() {
		return matrixWidth;
	}

	public void setMatrixWidth(Integer matrixWidth) {
		this.matrixWidth = matrixWidth;
	}
}