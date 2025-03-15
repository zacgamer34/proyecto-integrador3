# Entidad User

## Descripción General
La entidad `User` representa a los usuarios del sistema, incluyendo administradores, estudiantes y profesores. Esta entidad maneja la información básica de autenticación y mantiene la relación con los cursos asignados.

## Atributos

| Atributo | Tipo | Descripción | Restricciones |
|----------|------|-------------|---------------|
| id | Long | Identificador único del usuario | - Generado automáticamente<br>- Llave primaria |
| email | String | Correo electrónico del usuario | - No puede ser nulo<br>- Debe ser único<br>- Debe tener formato válido de email |
| password | String | Contraseña del usuario | - No puede ser nulo<br>- Mínimo 8 caracteres |
| role | Role (Enum) | Rol del usuario en el sistema | - Valores permitidos: ADMINISTRATOR, STUDENT, TEACHER |
| createdAt | LocalDateTime | Fecha y hora de creación | - Generado automáticamente<br>- No actualizable |
| updatedAt | LocalDateTime | Fecha y hora de última actualización | - Actualizado automáticamente |
| course | Course | Curso al que pertenece el usuario | - Relación muchos a uno con Course |

## Enumeración Role

```java
public enum Role {
    ADMINISTRATOR,  // Administrador del sistema
    STUDENT,        // Estudiante
    TEACHER         // Profesor
}
```

## Anotaciones JPA

### Anotaciones de Entidad
- `@Entity`: Marca la clase como una entidad JPA
- `@Table(name = "users")`: Define el nombre de la tabla en la base de datos

### Anotaciones de Atributos
- `@Id`: Marca el campo como llave primaria
- `@GeneratedValue`: Configura la generación automática de IDs
- `@Column`: Configura propiedades de la columna (nullable, unique)
- `@Email`: Valida el formato del correo electrónico
- `@Size`: Valida la longitud mínima de la contraseña
- `@Enumerated`: Define el tipo de almacenamiento para el enum
- `@CreationTimestamp`: Genera automáticamente la fecha de creación
- `@UpdateTimestamp`: Actualiza automáticamente la fecha de modificación

## Relaciones

### Relación con Course
- Tipo: Muchos a Uno (Many-to-One)
- Anotaciones:
  - `@ManyToOne(fetch = FetchType.LAZY)`: Define la relación con carga perezosa
  - `@JsonBackReference`: Evita la recursión infinita en la serialización JSON

## Ejemplo de Uso

```java
// Crear un nuevo usuario
User user = new User();
user.setEmail("profesor@ejemplo.com");
user.setPassword("contraseña123");
user.setRole(Role.TEACHER);

// Asignar un curso al usuario
Course course = // obtener curso
user.setCourse(course);
```

## Consideraciones

1. **Seguridad**:
   - La contraseña debe ser encriptada antes de almacenarse
   - El email debe ser único para evitar duplicados

2. **Validaciones**:
   - El email debe tener un formato válido
   - La contraseña debe tener al menos 8 caracteres

3. **Relaciones**:
   - Un usuario solo puede pertenecer a un curso a la vez
   - La relación con Course usa carga perezosa para optimizar el rendimiento

4. **Auditoría**:
   - Se mantiene registro automático de fechas de creación y actualización
   - Las fechas se generan y actualizan automáticamente