package py.edu.uca.fcyt.toluca.table;

import py.edu.uca.fcyt.toluca.table.state.*;
import py.edu.uca.fcyt.toluca.table.animation.Animable;
import java.awt.image.*;
import java.awt.geom.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.geom.AffineTransform;
import java.awt.Graphics2D;

/**
 * Contiene una seña
 * @author Grupo Interfaz de Juego
 */
class SignState implements State
{
	private int sign;
	
	// construye un nuevo SignState
	public SignState(int sign)	
	{ 
		Util.verifParam(Sign.isSign(sign), "Parámetro 'sign' inválido");
		this.sign = sign; 
	}
	
	public void transition(State state, double ratio) 
	{
		sign = ((SignState) state).getSign();
	}
	
	public int getSign() { return sign; }
	
	public State getCopy()
	{
		return new SignState(sign);
	}
}
