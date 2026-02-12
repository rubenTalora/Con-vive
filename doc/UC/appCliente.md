@startuml
left to right direction

actor "Usuario" as Usuari
actor "Administrador" as Admin

rectangle "Server" {
  usecase "Comprobar token" as CT
  usecase "Chatear con otros usuarios" as CH
  usecase "Eliminar publicación propia" as TS
  usecase "Eliminar publicación" as EP
}

' Relaciones del Usuario
Usuari --> CT
Usuari --> CH
Usuari --> TS


' Relaciones del Administrador
Admin --> EP
@enduml