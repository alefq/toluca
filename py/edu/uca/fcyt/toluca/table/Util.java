package py.edu.uca.fcyt.toluca.table;

import java.awt.*;
import javax.swing.*;
import java.awt.image.*;
import java.awt.geom.*;
import java.io.*;

class Util
{
	public static float cardScale = .75f;

	// dibuja 'iIcon' en 'bImage'
	static public void copyImage(ImageIcon iIcon, BufferedImage bImage)
	{
		bImage.createGraphics().drawImage
		(
			iIcon.getImage(),
			new AffineTransform(),
			iIcon.getImageObserver()
		);
	}

	// si 'exp' es falso arroja una excepción con mensaje 'msj'
	static public void verif(boolean exp, String msj)
	{
		if (!exp) throw new RuntimeException(msj);
	}

	static public int sgn(int num)
	{
		if (num > 0) return 1;
		else if (num < 0) return -1;
		else return 0;
	}

	static public String lineInput(String msg, int nChars)
	{
		byte[] b = new byte[nChars];
		int r;

		System.out.print(msg);

		try
		{
			r = System.in.read(b);
			return new String(b, 0, r - 2);
		} catch (IOException e)
		{
			return null;
		}
	}

}