## P2.1

### Situacion 1 - Observer

Socios solo avisa "se vencio la membresia" y los interesados se suscriben.
Sin esto, cada modulo nuevo (como promociones) obliga a abrir socios y agregar otra llamada.
Con Observer solo agrego un suscriptor y no toco socios.

### Situacion 2 - Strategy

Cada franja (mañana, noche, fin de semana) es una forma distinta de calcular la tarifa.
Sin esto, el if/else sigue copiado en cobros y cotizaciones y cada cambio de reglas hay que hacerlo dos veces.
Con una clase por franja la regla queda en un solo lugar.

### Situacion 3 - Adapter

El SDK no se puede modificar y trabaja en ingles, centavos y tokens.
Sin esto, ChargeCard queda regado por todo el codigo y cambiar de proveedor obliga a corregir todo.
Con un adaptador uso mi propia interfaz y si cambian de proveedor solo hago otro adaptador.
