# Diagrama C4  Nivel 3 springboot

```plantuml

@startuml

!include https://raw.githubusercontent.com/plantuml-stdlib/C4-PlantUML/master/C4_Container.puml

LAYOUT_LEFT_RIGHT()

title Diagrama C4 Nivel 3 - Con!Vive (Publicaciones)

Container(AppClient, "APP/Web", "Dart", "App móvil o web")

ContainerDb(AppDB, "Base de Datos App", "MySQL")

System_Boundary(appPublicaciones, "Backend - Publicaciones (Spring Boot)") {

  System_Boundary(partUser, " Usuario ") {

    Container(CrearPublicacion, "Crear Publicación")

    Container(EditarPublicacion, "Editar Publicación")

    Container(EliminarPublicacionPropia, "Eliminar Publicación Propia")

    Container(ConsultarPublicaciones, "Consultar / Filtrar Publicaciones")

    Container(GestionPerfil, "Gestión de Perfil Usuario")
  }

  System_Boundary(partAdmin, " Administrador ") {

    Container(EliminarCualquierPublicacion, "Eliminar cualquier publicación")

    Container(BanearUsuario, "Banear Usuario")
  }

  Container(AuthToken, "Validar Token y Rol", "JWT", "Comprueba permisos de usuario o admin")

}

Rel(AppClient, AuthToken, "Envía token para validación")

Rel(AuthToken, CrearPublicacion, "Autoriza si es usuario válido")
Rel(AuthToken, EditarPublicacion, "Autoriza si es propietario")
Rel(AuthToken, EliminarPublicacionPropia, "Autoriza si es propietario")
Rel(AuthToken, ConsultarPublicaciones, "Permite acceso")
Rel(AuthToken, GestionPerfil, "Autoriza")

Rel(AuthToken, EliminarCualquierPublicacion, "Autoriza si es admin")
Rel(AuthToken, BanearUsuario, "Autoriza si es admin")

Rel(CrearPublicacion, AppDB, "INSERT")
Rel(EditarPublicacion, AppDB, "UPDATE")
Rel(EliminarPublicacionPropia, AppDB, "DELETE")
Rel(ConsultarPublicaciones, AppDB, "SELECT")
Rel(GestionPerfil, AppDB, "UPDATE / SELECT")

Rel(EliminarCualquierPublicacion, AppDB, "DELETE")
Rel(BanearUsuario, AppDB, "UPDATE estado_usuario")

Rel(AppClient, CrearPublicacion, "Crea")
Rel(AppClient, EditarPublicacion, "Edita")
Rel(AppClient, EliminarPublicacionPropia, "Elimina")
Rel(AppClient, ConsultarPublicaciones, "Consulta")
Rel(AppClient, GestionPerfil, "Gestiona perfil")

Rel(AppClient, EliminarCualquierPublicacion, "Admin elimina")
Rel(AppClient, BanearUsuario, "Admin banea")

@enduml