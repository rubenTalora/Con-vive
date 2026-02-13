# Diagrama C4 Nivell 2

```plantuml
@startuml
!include https://raw.githubusercontent.com/plantuml-stdlib/C4-PlantUML/master/C4_Container.puml

LAYOUT_LEFT_RIGHT()

title Diagrama C4 Nivel 2

Person(User, "Usuario", "Usuario que accede vía web o móvil")
Person(Admin, "Admin", "Admin que accede vía web o móvil")

System_Boundary(app, "Con!Vive") {
    System_Boundary(front, "Frontend") {
    
    ' Client Movil
    Container(Dart, "APP/Web", "Dart", "Web")

    ' Portal Odoo
    Container(webOdoo, "Portal Web" , "HTML, CSS, JS", "Web donde gestionar suscripcions")
    }
    System_Boundary(back, "Backend") {
    ' Server de a app
    Container(Publicaciones, "Publicaciones", "Java, Spring Boot, hibernate")

    ' Server Odoo
    Container(serverOdoo, "Servicio de Odoo", "Odoo", "Servidor donde se gestionaran todos los usuarios y suscripciones")

    ContainerDb( AppDB, "DB de la app", "Mysql", "Almacena toda la info de la app")

    ContainerDb( odooDB, "DB del servidor Odoo", "PostgresSql", "Almacena toda la información del servidor Odoo")
    }
}

Rel(User, Dart, "Accede a")
Rel(User,webOdoo,"Entra a")

Rel(Admin, Dart, "Gestiona")
Rel(Admin,webOdoo,"Gestiona")

Rel(Dart,serverOdoo,"Pide token")

Rel(Publicaciones, AppDB, "Consulta")
Rel(Publicaciones, AppDB, "elimina")
Rel(Publicaciones, AppDB, "edita")
Rel(Publicaciones, AppDB, "añade")

Rel(Dart, Publicaciones, "Consulta")
Rel(Dart, Publicaciones, "Sube")
Rel(Dart, Publicaciones, "elimina")
Rel(Dart, Publicaciones, "edita")



Rel(webOdoo, serverOdoo, "Gestiona")

Rel(serverOdoo, odooDB, "Gestiona")

@enduml
```
