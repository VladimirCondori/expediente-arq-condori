# Parte 3 — El patrón

Autor: Vladimir Condori

## Requerimiento
"Cuando un pedido queda preparado, el estudiante debe recibir un aviso."

## Patrón aplicado: Observer
Es un evento (el pedido pasa a PREPARADO) al que deben reaccionar uno o más interesados, sin que el pedido sepa quiénes son.

## Diseño

```mermaid
classDiagram
    note "Autor: Vladimir Condori"

    class Pedido {
        -observadores: List~IObservadorPedido~
        -estado: EstadoPedido
        +Suscribir(o: IObservadorPedido)
        +Desuscribir(o: IObservadorPedido)
        +MarcarPreparado()
        -Notificar()
    }
    class IObservadorPedido {
        <<interface>>
        +Actualizar(p: Pedido)
    }
    class AvisoEstudiante {
        -canal: INotificador
        +Actualizar(p: Pedido)
    }
    class PantallaDeRetiro {
        +Actualizar(p: Pedido)
    }
    class INotificador {
        <<interface>>
        +Enviar(mensaje: string)
    }

    Pedido o-- "0..*" IObservadorPedido : avisa a
    IObservadorPedido <|.. AvisoEstudiante
    IObservadorPedido <|.. PantallaDeRetiro
    AvisoEstudiante --> INotificador : usa
```
