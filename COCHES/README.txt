Requerimiento 1

Esta práctica consiste en la implementación de un programa Java para la gestión del almacén de coches en un concesionario. Los coches tendrán los siguientes atributos, id, matricula, marca, modelo y color.

El usuario interactuará con el programa a través del siguiente menú, que servirá como interfaz.

Añadir nuevo coche
Borrar coche por id
Consulta coche por id
Listado de coches
Terminar el programa
Nada más comenzar la ejecución del programa se debe verificar si existe el fichero coches.dat (fichero que contendrá objetos Coche). Si existe, debes leerlo para llenar una colección de tipo ArrayList con todos los objetos Coche existentes en el fichero. Si no existe el archivo coches.dat, no tendrás nada que hacer por el momento, pero sí debes dejar la colección ArrayList disponible, aunque esté vacía.

Las opciones del menú 1 a 5 trabajarán sobre la colección de tipo ArrayList para añadir, borrar, consultar o listar, y no sobre el fichero coches.dat.

Cuando el usuario decida terminar la ejecución del programa pulsando la opción 5, el programa deberá crear el fichero coches.dat, sobrescribiendo el anterior si existiera. Se escribirán en el fichero tantos coches como elementos tenga la colección ArrayList que has creado.


Requerimiento 2

Se añadirá una opción al menú que será “Exportar coches a archivo CSV”, que creará un fichero (coches.csv) donde guardará la información de los coches con el formato de un archivo csv, es decir, valores separados por el carácter “;”. Comprobar que dicho fichero se puede abrir con un programa como Excel o alguna herramienta en online.


Requerimiento 3

No se permite duplicar el id ni la matricula.