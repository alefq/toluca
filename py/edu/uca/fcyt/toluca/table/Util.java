package py.edu.uca.fcyt.toluca.table;

import py.edu.uca.fcyt.toluca.table.state.*;
import java.awt.*;
import java.util.*;
import javax.swing.*;
import java.awt.image.*;
import java.awt.geom.*;
import java.io.*;
import java.security.InvalidParameterException;
import py.edu.uca.fcyt.game.*;

public class Util
{
	public static float cardScale = .75f;

	// dibuja 'iIcon' en 'bImage'
	static public void copyImage(ImageIcon iIcon, BufferedImage bImage)
	{
		// verificaciones
		Util.verifParam(iIcon != null, "Parámetro 'iIcon' nulo");
		Util.verifParam(bImage != null, "Parámetro 'bImage' nulo");
		
		bImage.createGraphics().drawImage
		(
			iIcon.getImage(),
			new AffineTransform(),
			iIcon.getImageObserver()
		);
	}

	/**
	 * si 'exp' es falso arroja una excepción 
     * InvalidParameterException con mensaje 'msj'
     */	
	static public void verifParam(boolean exp, String msj)
	{
		if (!exp) throw new InvalidParameterException(msj);
	}

	/**
	 * si 'exp' es falso arroja una excepción 
     * RuntimeException con mensaje 'msj'
     */	
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
	
	static public void sleep(long msecs)
	{
		long tAct;
		
		tAct = System.currentTimeMillis();
		while (tAct + msecs > System.currentTimeMillis());
	}
	
	/**
     * Duerme el Thread 't' por 'millis' milisegundos.
     * Si la dormida es interrumpida, lanza un 
     * RuntimeException basado en el InterruptedException
     * arrojado
     */
	public static void sleep(Thread t, long millis)
	{
		try
		{
			t.sleep(millis);
		}
		catch(InterruptedException e)
		{
			throw new RuntimeException(e);
		}
	}

	/**
     * Retorna el nombre del palo 'palo'
     */
    public static String getKindName(int kind)
    {
    	switch (kind)
    	{
    		case Card.BASTO: return "basto";
    		case Card.COPA: return "copa";
    		case Card.ORO: return "oro";
    		case Card.ESPADA: return "espada";
    		default:
    			throw new InvalidParameterException
    			(
    				"Argumento 'kind' inválido"
    			);
    	}
    }

	/** 
	 * Convierte 'ang' en un ángulo en el rango [-PI/2, PI/2)
	 */
	public static double normAng(double ang)
	{
		while (ang >= Math.PI / 2) ang -= Math.PI;
		while (ang < -Math.PI / 2) ang += Math.PI;

		return ang;
	}	

}