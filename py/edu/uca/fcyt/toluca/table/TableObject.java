package py.edu.uca.fcyt.toluca.table;


// cualquier objeto en la mesa
interface TableObject
{
	// establece el i-�simo estado del objeto 
	public void setState
	(
		int ind, float x, float y, float angle, float scale
	);
	
	// establece el �ltimo estado como el primero 
	// y al �ltimo carga los datos pasados
	public void pushState
	(
		float x, float y, float angle, float scale
	);
	
	// establece el tiempo en el cual el objeto
	// debe pasar del estado 0 al 1
	public void setTimes(long startTime, long durationTime);
}		