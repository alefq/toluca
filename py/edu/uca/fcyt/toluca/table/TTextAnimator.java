/*
 * Created on 02/09/2003
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package py.edu.uca.fcyt.toluca.table;

import java.awt.Color;
import java.awt.Font;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.Vector;

import py.edu.uca.fcyt.toluca.table.animation.Animable;
import py.edu.uca.fcyt.toluca.table.state.StateListener;

/**
 * @author Roberto
 *
 * Maneja la animaci�n del texto.
 * 
 */
class TTextAnimator implements Animable, StateListener
{
	// textos a mostrar
	private Vector ttGroups;
	private BufferedImage[] biOut;
	private AffineTransform afTrans;
	
	private static final Color darkGreen = getColorAlpha
	(
		Color.GREEN.darker(), 1
	);
	
	// colores usados para desvanecer texto
	private static final Color[] darkGreen3 = new Color[]
	{
		darkGreen, 
		darkGreen, 
		darkGreen
	};

	private static final Color[] darkGreen2 = new Color[]
	{
		darkGreen, 
		darkGreen 
	};
	
	/** Construye un nuevo TextAnimator */
	public TTextAnimator()
	{
		ttGroups = new Vector();
	}
	
	/** Muestra la presentaci�n inicial */
	public void showPresentation()
	{
		createTitle();
		createSubTitle();
		fadeOutPresent();
	}
	
	// crea el t�tulo
	private void createTitle()
	{
		TTextGroup ttgTitle;
		TableText[] tTexts;
		Font font;
		char[] chTitle;
		double[] deltaPos;
		int pos, x, y, size = 180;
		double rad, ang;
		Color[][] colors;

		colors = new Color[][]
		{
			new Color[]
			{
				Color.BLACK, 
				Color.BLUE.darker(), 
				Color.BLUE.darker()
			},
			new Color[]
			{
				Color.BLACK, 
				Color.RED, 
				Color.YELLOW
			}
		};
		
		// el texto estar� a -120 puntos verticales del centro
		ttgTitle = new TTextGroup(0, -150);

		font = new Font("SansSerif", Font.BOLD, size);
		
		// obtiene los caracteres y las separaciones del texto
		chTitle = "Toluca".toCharArray();
		deltaPos = new double[]{.45, .4, .4, .55, .55, 0};
		
		// crea el vector de TableText (cada uno con una letra)
		tTexts = new TableText[chTitle.length];
		
		// posici�n inicial horizontal y �ngulo inicial 
		pos = -(int) (size * 1.175);
		ang = -Math.PI * .4;
		
		// crea los TableTexts
		for (int i = 0; i < tTexts.length; i++)
		{
			// el texto comenzar� con una posici�n el�ptica
			rad = 1000;
			x = (int) (rad * Math.sin(ang));
			y = (int) (rad * Math.cos(ang)) + 100;
			y *= ((i % 2) * 2 - 1) * .3;

			// crea el TableText letra y lo agrega al t�tulo
			tTexts[i] = new TableText
			(
				chTitle[i], font, 3, Math.PI / 4
			);
			ttgTitle.addTText(tTexts[i]);
			
			// carga sus datos de animaci�n
			tTexts[i].pushState
			(
				x, y, Math.random() * 15 + 5, 40, 0, colors[0] 
			);
			tTexts[i].pushState
			(
				pos, 0, 0, 1, 7000 + i * 200, colors[1]
			);
			
			pos += deltaPos[i] * size;
			ang += Math.PI / tTexts.length;
		}
		
		// agrega este objeto como listener de eventos 
		ttgTitle.addStateListener(this);
		
		// agrega el subt�tulo al vector 
		ttGroups.add(ttgTitle);
	}
	
	// crea el subt�tulo
	private void createSubTitle()
	{
		TTextGroup ttgSubTitle;
		TableText[] tTexts;
		Font font;
		long remTime;
		Color col[], colors[][];
		
		col = new Color[]
		{
			Color.BLACK, 
			Color.CYAN, 
			Color.getHSBColor(.6f, .7f, 1)
		};
		
		colors = new Color[][]
		{
			getColorAlpha(col, 0),
			getColorAlpha(col, 1),
		};
		
		// obtiene el tiempo que falta para que termine
		// la animaci�n anterior
		remTime = getRemainingTime();

		// el subt�tulo estar� a 100 puntos por debajo del centro
		ttgSubTitle = new TTextGroup(0, -20);
		font = new Font("Arial", Font.BOLD, 60);
		
		// crea el subt�tulo
		tTexts = new TableText[]
		{
			new TableText("Truco Online", font, 2, Math.PI * 1.5),		
			new TableText("de la", font, 2, Math.PI * 1.5),		
			new TableText("Universidad Cat�lica", font, 2, Math.PI * 1.5),		
			new TableText("de Asunci�n", font, 2, Math.PI * 1.5)
		};
		
		// agrega los textos al subt�tulo
		for (int i = 0; i < tTexts.length; i++)
		{
			// agrega el texto y carga sus datos de animaci�n
			ttgSubTitle.addTText(tTexts[i]);
			tTexts[i].pushPause(i * 1000 + remTime);
			tTexts[i].pushState
			(
				0, i * 65 + 100, 0, 1, 1000, colors[0] 
			);
			tTexts[i].pushState
			(
				0, i * 65, 0, 1, 500, colors[1]
			);
		}

		// agrega este objeto como listener de eventos 
		ttgSubTitle.addStateListener(this);
		
		// carga el subt�tulo al vector
		ttGroups.insertElementAt(ttgSubTitle, 0);
	}
	
	/** Desvanece la presentaci�n */
	private void fadeOutPresent()
	{
		TTextGroup ttGroup;
		TableText tText;
		
		pushNormalizedPause(2000);

		ttGroup = (TTextGroup) ttGroups.get(1);
		
		for (int i = 0; i < ttGroup.getTTCount(); i++)
		{
			tText = ttGroup.getTText(i);
			
			// desaparici�n del t�tulo
			tText.pushState
			(
				(int) (i * 14), 0, 0, 1, 500, darkGreen3 
			);
		}

		ttGroup = (TTextGroup) ttGroups.get(0);

		for (int i = 0; i < ttGroup.getTTCount(); i++)
		{
			tText = ttGroup.getTText(i);

			tText.pushState
			(
				0, i * 90 - 25, 0, 1, 500, darkGreen3 
			);
		}
	}

	public void showIndications()
	{
		String[] texts;
		Color[] col;
//		Thread th;
		
		col = new Color[]
		{
			Color.BLACK, 
			Color.YELLOW, 
			Color.ORANGE
		};
		
		texts = new String[]
		{
			"Haz click en una silla para sentarte.", 
			"S�lo el que est� en la silla de posici�n baja " +   
			"(la roja) puede iniciar un juego.",
			"El que tiene borde amarillo posee el turno.",
			"Cuando sea tu turno, haz click derecho en " + 
			"la mesa para las acciones � utiliza los" + 
			"botones del panel \"Jugadas\"."
		};
		showStrings(texts, col, -130, 0, 10000);
	}
	
	public void showStrings
	(
		String[] texts, Color[] colors, int deltaY, 
		long beforeDelay, long afterDelay 
	)
	{
		TTextGroup ttGroup;
		TableText tText;
		Font font;
		long rTime;
		Color[][] aColors;
		String[] lines;
		
		aColors = new Color[][]
		{
			getColorAlpha(colors, 0),
			getColorAlpha(colors, 1)
		};
		
		rTime = getRemainingTime() + beforeDelay;
		
		ttGroup = new TTextGroup(0, deltaY);
		
		font = new Font("Arial", Font.BOLD, 30);
		
		for (int i = 0, k = 0; i < texts.length; i++)
		{
			lines = Util.getLines(texts[i], 40);
			for (int j = 0; j < lines.length; j++, k++)
			{
				tText = new TableText
				(
					lines[j], font, .6f, Math.PI * .5
				);
				ttGroup.addTText(tText);
	
				tText.pushPause(i * 3000 + rTime);
				tText.pushState(0, k * 30 + i * 10, 0, 1, 0, aColors[0]);
				tText.pushState(0, k * 30 + i * 10, 0, 1, 500, aColors[1]);
			}
		}
		
		ttGroup.pushNormalizedPause(afterDelay);
		
		for (int i = 0; i < ttGroup.getTTCount(); i++)
		{
			tText = ttGroup.getTText(i); 
			tText.pushPause(i * 200);
			tText.pushState(0, i * 30, 0, 1, 500, aColors[0]);
		}
		
		ttGroup.addStateListener(this);
		ttGroup.setOut(biOut, afTrans);
		ttGroups.insertElementAt(ttGroup, 0);
	}
	

	public void setOut
	(
		BufferedImage[] biOut, AffineTransform afTrans
	)
	{
		this.biOut = biOut;
		this.afTrans = afTrans;

		for (int i = 0; i < ttGroups.size(); i++)
			((TTextGroup) ttGroups.get(i)).setOut(biOut, afTrans);
	}

	public void paint(int buffIndex)
	{
		for (int i = 0; i < ttGroups.size(); i++)
			((TTextGroup) ttGroups.get(i)).paint(buffIndex);
	}

	public void advance()
	{
		for (int i = 0; i < ttGroups.size(); i++)
			((TTextGroup) ttGroups.get(i)).advance();
	}
	
	/* (non-Javadoc)
	 * @see py.edu.uca.fcyt.toluca.table.state.StateListener#animationCompleted(java.lang.Object)
	 */
	public void animationCompleted(Object o)
	{
		ttGroups.remove(o);
	}

	/* (non-Javadoc)
	 * @see py.edu.uca.fcyt.toluca.table.state.StateListener#transitionCompleted(java.lang.Object)
	 */
	public void transitionCompleted(Object o)
	{

	}

	/** Retorna el tiempo que falta para que termine 
	 * la animaci�n */ 
	public long getRemainingTime()
	{
		long maxTime = 0, aTime;
		
		for (int i = 0; i < ttGroups.size(); i++)
		{
			aTime = ((TTextGroup) ttGroups.get(i)).getRemainingTime();
			if (maxTime < aTime) maxTime = aTime;
		}
		
		return maxTime;
	}
	
	/** Normaliza la duraci�n de todos los TTextGroups.
	 * Agrega una pausa a todos los TableTexts de tal
	 * manera que todas terminen al mismo tiempo. El
	 * TTextGroup de mayor duraci�n no tiene pausa agregada.  
	 */
	public void pushNormalizedPause(long duration)
	{
		long remTime;
		TTextGroup ttGroup;
		
		remTime = getRemainingTime();

		for (int i = 0; i < ttGroups.size(); i++)
		{
			ttGroup = (TTextGroup) ttGroups.get(i);
			ttGroup.pushNormalizedPause(remTime - ttGroup.getRemainingTime() + duration);
		}
	}	
	
	/** Agrega una pausa a todos los {@link TableText}s */
	public void pushPause(long duration)
	{
		for (int i = 0; i < ttGroups.size(); i++)
		{
			((TableText) ttGroups.get(i)).pushPause(duration);
		}
	}
	
	/** Elimina cualquier animaci�n actual */
	public void clearAll()
	{
		ttGroups.clear();
	}
	
	// retorna el color pasado con un alpha
	private static Color getColorAlpha(Color color, float alpha)
	{
		float[] comp;
		
		comp = color.getColorComponents(null);
		return new Color(comp[0], comp[1], comp[2], alpha);
	}
	
	// retorna los colores pasados con un alpha
	private Color[] getColorAlpha(Color[] colors, float alpha)
	{
		float[] comp;
		Color[] ret;
		
		ret = new Color[colors.length];
		
		for (int i = 0; i < colors.length; i++)
			ret[i] = getColorAlpha(colors[i], alpha);
		
		return ret;
	}
}
