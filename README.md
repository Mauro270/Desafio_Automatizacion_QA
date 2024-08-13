Prueba técnica
Se realiza documento explicativo de la prueba técnica solicitada para postulación a puesto de QA automatizador.  
 A continuación indico los detalles a considerar: 
1.- Se realizan 5 casos de prueba con los siguientes nombres: TC_001, TC_002, TC_003, TC_004, TC_005.
2.- Los casos de prueba se deben ejecutar de forma correlativa desde el 1 al 5. La forma de ejecución es ingresar al caso, presionar click derecho y seleccionar opción Junit Test Case.
3.- Cada caso de prueba tiene el escenario al inicio de los script, dando descripción y paso a paso de cada acción realizada.
4.- cada caso de prueba posee la siguiente línea de código: String filepath = "C:\\Users\\Mauricio\\Desktop\\data.xlsx"; (Con ella se obtienen los datos de Excel usados para las pruebas).
Se debe modificar ruta para que apunte al escritorio del equipo que se realizará la prueba de los casos.
5.- Las evidencias en imágenes (PNG) queda guardada en carpeta raíz Tareget -- > Screenshot
En caso de no ver las evidencias, se debe posicionar sobre carpeta screenshot presionar botón derecho y ejecutar “Refresh”
6.- Las validaciones de texto solicitadas se realizan con Junit, específicamente la versión 4 
7.- Este proyecto Maven no necesita la instalación de Chrome driver ya que se realizó usando Webdrivermanager.
