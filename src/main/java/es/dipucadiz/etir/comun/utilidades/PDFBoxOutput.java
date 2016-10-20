package es.dipucadiz.etir.comun.utilidades;

import java.awt.Color;
import java.io.IOException;

import org.apache.pdfbox.pdmodel.PDPageContentStream;

import net.sourceforge.barbecue.output.LabelLayout;
import net.sourceforge.barbecue.output.Output;
import net.sourceforge.barbecue.output.OutputException;

public class PDFBoxOutput implements Output {

	/** The widths and heights from Barbecue are multipplied with this scalar to get the widths and heights for PDFBox. */
	public final static float SCALAR = 0.5f;

	private final PDPageContentStream stream;
	private final float startX;
	private final float startY;
	private final float height;
	private boolean toggleDrawingColor;
	private float widthScalar;

	PDFBoxOutput(PDPageContentStream stream, float startX, float startY, float height, float widthScalar) {
		this.stream = stream;
		this.startX = startX;
		this.startY = startY;
		this.height = height;
		this.widthScalar = widthScalar;
	}

	@Override
	public void beginDraw() throws OutputException {}

	@Override
	public int drawBar(int x, int y, int width, int height, boolean paintWithForegroundColor) throws OutputException {
		if (paintWithForegroundColor == !toggleDrawingColor) {
			try {
				stream.setLineWidth(0.0f);
				stream.setStrokingColor(Color.BLACK);
				//                stream.fillRect(startX + SCALAR * x, startY - SCALAR * y, SCALAR * width, this.height);
				stream.addRect(startX + SCALAR * x * widthScalar, startY - SCALAR * y, SCALAR * width * widthScalar, this.height);
				stream.fill();
				stream.stroke();
			} catch (IOException e) {
				throw new OutputException(e);
			}
		}
		return width;
	}

	@Override
	public int drawText(String text, LabelLayout layout) throws OutputException {
		return 0;
	}

	@Override
	public void endDraw(int width, int height) throws OutputException {}

	@Override
	public void paintBackground(int x, int y, int width, int height) {}

	@Override
	public void toggleDrawingColor() {
		toggleDrawingColor = !toggleDrawingColor;
	}

}