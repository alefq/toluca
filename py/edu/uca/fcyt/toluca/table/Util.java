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
import py.edu.uca.fcyt.toluca.event.*;
import py.edu.uca.fcyt.toluca.game.*;

public class Util
{
	public static float cardScale = .75f;
	private static final Hashtable pNames = getPlayNames();
	private static final Hashtable eNames = getEventNames();
	private static final byte[] plays = getPlays();

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

	static public void sleep(py.edu.uca.fcyt.toluca.table.animation.Animator anim, long msecs)
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
	public static void wait(Object t, long millis)
	{
		try
		{
			synchronized(t)
			{
				t.wait(millis);
			}
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

	/**
     * Retorna un Hashtable con los pares 
     * (jugada: Byte, nombre: String)
     */
	private static Hashtable getPlayNames()
	{
		Hashtable ret;
		
		ret = new Hashtable();
		ret.put(new Byte(TrucoPlay.ENVIDO), "Envido");
		ret.put(new Byte(TrucoPlay.REAL_ENVIDO), "Real Envido");
		ret.put(new Byte(TrucoPlay.FALTA_ENVIDO), "Falta Envido");
		ret.put(new Byte(TrucoPlay.FLOR), "Flor");
		ret.put(new Byte(TrucoPlay.TRUCO), "Truco");
		ret.put(new Byte(TrucoPlay.QUIERO), "Quiero");
		ret.put(new Byte(TrucoPlay.RETRUCO), "Quiero Retruco");
		ret.put(new Byte(TrucoPlay.VALE_CUATRO), "Quiero Vale 4");
		ret.put(new Byte(TrucoPlay.NO_QUIERO), "No quiero");
		ret.put(new Byte(TrucoPlay.CANTO_ENVIDO), "Cantar");
		ret.put(new Byte(TrucoPlay.PASO_ENVIDO), "Paso envido");
		ret.put(new Byte(TrucoPlay.CERRARSE), "Cerrarse");
		ret.put(new Byte(TrucoPlay.ME_VOY_AL_MAZO), "Me voy al mazo");
		return ret;
    }

	/**
     * Retorna un Hashtable con los pares 
     * (evento: Byte, nombre: String)
     */
	private static Hashtable getEventNames()
	{
		Hashtable ret;
		
		ret = new Hashtable();
		ret.put(new Byte(TrucoEvent.ENVIDO), "Envido");
		ret.put(new Byte(TrucoEvent.REAL_ENVIDO), "Real Envido");
		ret.put(new Byte(TrucoEvent.FALTA_ENVIDO), "Falta Envido");
		ret.put(new Byte(TrucoEvent.FLOR), "Flor");
		ret.put(new Byte(TrucoEvent.TRUCO), "Truco");
		ret.put(new Byte(TrucoEvent.QUIERO), "Quiero");
		ret.put(new Byte(TrucoEvent.RETRUCO), "Quiero Retruco");
		ret.put(new Byte(TrucoEvent.VALE_CUATRO), "Quiero Vale 4");
		ret.put(new Byte(TrucoEvent.NO_QUIERO), "No quiero");
		ret.put(new Byte(TrucoEvent.CANTO_ENVIDO), "Cantar puntos");
		ret.put(new Byte(TrucoEvent.PASO_ENVIDO), "Paso envido");
		ret.put(new Byte(TrucoEvent.CERRARSE), "Cerrarse");
		ret.put(new Byte(TrucoEvent.ME_VOY_AL_MAZO), "Me voy al mazo");
		return ret;
    }

	/**
     * Retorna un array con las jugadas en 
     * el orden en que se deben mostrar
     */
	private static byte[] getPlays()
	{
		byte[] ret;
		
		ret = new byte[]
		{
			TrucoPlay.ENVIDO,
			TrucoPlay.REAL_ENVIDO,
			TrucoPlay.FALTA_ENVIDO,
			TrucoPlay.FLOR,
			TrucoPlay.TRUCO,
			TrucoPlay.QUIERO,
			TrucoPlay.RETRUCO,
			TrucoPlay.VALE_CUATRO,
			TrucoPlay.NO_QUIERO,
			TrucoPlay.CANTO_ENVIDO,
			TrucoPlay.PASO_ENVIDO,
			TrucoPlay.CERRARSE,
			TrucoPlay.ME_VOY_AL_MAZO
		};
		
		return ret;
    }

	/**
     * Retorna el nombre de una jugada
     */	
    public static String getPlayName(byte play)
    {
    	return (String) pNames.get(new Byte(play));
    }

	/**
     * Retorna el nombre de un evento
     */	
    public static String getEventName(byte play)
    {
    	return (String) eNames.get(new Byte(play));
    }

	/**
     * Retorna una jugada. 
     * @param index		Indice de la jugada a retornar.
     */    
    public static byte getPlay(int index)
    {
    	return plays[index];
    }
    
    /**
     * Retorna la cantidad de jugadas posibles.
     */
    public static int getPlayCount()
    {
    	return plays.length;
    }

    private static boolean message(String msg, boolean ret)
    {
    	System.out.println(msg);
    	return ret;
    }
    
    
    
    public static void main(String[] args)
    {
		new Object()
		{
			protected void finalize()
			{
				System.out.println("Hola");
			}
		};
		System.gc();
    }
    
    /**
     * Retorna el subdirectorio de imágenes
     */
    public static String getImagesDir()
    {
    	return "E:/Mis documentos/Programas/Java/Toluca/Table/py/edu/uca/fcyt/toluca/images/";
    }
}