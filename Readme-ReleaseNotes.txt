---------------------------------------------------------------------
TAG 3.0
---------------------------------------------------------------------
NEW FEATURES:
* Se segurizan las apis que necesitan de un login para ser usadas(JWT)
* Se completan los endpoints de CREAR de Intencion y Transaccion
* Se agregas mas tests de los distintos niveles(Controllers,Services,Modelo,Arquitectura)
* Se agregan enpoints respecto a Crypto para obtener cotizaciones , tambien por
un rango de fechas , volumen operado de una crypto de un usuario , y mas.
* Se completa el circuito de la intencion/transaccion(ya estaba la logica seudohecha pero
no implementada completamente)
* Se loguea los controllers usando un aspecto
* Se implemento Ehcache para el listado de la ultima cotizacion de cada crypto

NOTES
* Pulir el swagger para que este mas lindo

KNOWN ISSUES:
* AntiPatter DTO
* Problema con los builds(no siento que estan de la mejor manera implementados)
---------------------------------------------------------------------
TAG 2.0
---------------------------------------------------------------------
NEW FEATURES:
* Se agrego las acciones para que el usuario pueda manifestar sus intenciones
* Se genero un scheduler para obtener periodicamente las cotizaciones.
* Se agregaron testing E2E de User
* Se agregaron endpoints de crypto para realizar consultas de las cotizaciones.
* Se crearon datos fakes para la BD
* El modelo es persistido en BD en memoria mediante HSQLDB

NOTES:
* Falta la funcionalidad para informar a un usuario el volumen operado de cripto activos entre dos fechas
* Falta funcionalidad de procesar una intencion
* Falta comprobar la funcionalidad de realizar transaciones
* Se agrego un tag correspondiente a la entrega 1

KNOWN ISSUES:
* AntiPatter DTO
* Problema con los builds(no siento que estan de la mejor manera implementados)
---------------------------------------------------------------------
TAG 1.0
---------------------------------------------------------------------
NEW FEATURES:
* Proveer servicio de registracion de usuario
* Swagger para documentar y exponer endpoints
* Deploy SonarCloud en la rama main
* Deploy Docker image en la rama docker
* Validaciones modelo/service
* Se persiste en H2 como memoria

NOTES:
* Clean Code según la materia
* La contraseña del usuario no esta encriptada
* Faltan test unitario

KNOWN ISSUES:
* Nos falto hacer el tag de esta entrega
