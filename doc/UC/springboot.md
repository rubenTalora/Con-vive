@startuml
left to right direction

actor "Usuario" as Usuari
actor "Administrador" as Admin

rectangle "Server" {
  usecase "Comprobar token" as CT
  usecase "Hacer Publicación" as CH
  usecase "Eliminar publicación propia" as TS
  usecase "Buscar piso o compañero" as TN
  usecase "Eliminar publicación" as EP
}

' Relaciones del Usuario
Usuari --> CT
Usuari --> CH
Usuari --> TS
Usuari --> TN


' Relaciones del Administrador
Admin --> EP
@enduml