package py.edu.uca.fcyt.toluca.table;

/**
 * Excepci�n arrojada cuando se intenta realizar 
 * alguna acci�n inv�lida en la mesa
 */
public class TableException extends RuntimeException 
{
	public TableException(String title)
	{
		super(title);
	}
}

//	public static final int CHAIR_TAKEN = 0;
//	public static final int PLAYER_SITTED = 1;
//	
//	protected int reason;
//	
//	public TableException(String title, int reason)
//	{
//		super(title);
//		this.reason = reason;
//	}
//	
//	public int getReason() { return reason; }
//}