package py.edu.uca.fcyt.game;

import java.lang.*;
import javax.swing.*;


public class Card
{
	public static final int ORO = 1;
	public static final int ESPADA = 2;
	public static final int COPA = 3;
	public static final int BASTO = 4;

	protected byte value;			// valor de la carta
	protected byte kind;			// palod de la carta
	protected boolean flipped;	// carta cerrada (flipped) o abierta

	// construye una carta
	public Card(int kind, int value)
	{
		setKind(kind);
		setValue(value);
	}

	// devuelven el valor y el palo de la carta, y si está abierta o no
	public int getValue() { return value; }
	public int getKind() { return kind; }
	public boolean isFlipped() { return flipped; }

	// establece el valor de la carta
	public Card setValue(int value)
	{
		if (value < 1 || value > 12) throw new RuntimeException("Valor inválido");
		this.value = (byte) value;
		return this;
	}

	// establece el palo de la carta
	public Card setKind(int kind)
	{
		if (kind < 1 || kind > 4) throw new RuntimeException("Palo inválido");
		this.kind = (byte) kind;
		return this;
	}

	// establece si la carta está abierta o no
	public Card setFlipped(boolean flipped)
	{
		this.flipped = flipped;
		return this;
	}


	// obtiene un 'ImageIcon' contiene al dibujo de la carta
	public ImageIcon getImageIcon()
	{
		String[] kinds = new String[] {"Oro", "Espada", "Copa", "Basto"};

		return new ImageIcon("..\\imagenes\\" + kinds[kind - 1] + "\\" + value + ".gif");
	}
}
