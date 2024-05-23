package model;

public class Alumno {
    private int id_alumno;
    private String codigo_alumno;
    private String dni;
    private String nombre;
    private String apellidos;
    private String fecha_nacimiento;
    private String mail;
    private String telefono;

    public Alumno() {
    }

    public Alumno(int id_alumno, String codigo_alumno, String dni, String nombre, String apellidos, String fecha_nacimiento, String mail, String telefono) {
        this.id_alumno = id_alumno;
        this.codigo_alumno = codigo_alumno;
        this.dni = dni;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.fecha_nacimiento = fecha_nacimiento;
        this.mail = mail;
        this.telefono = telefono;
    }

    public int getId_alumno() {
        return id_alumno;
    }

    public void setId_alumno(int id_alumno) {
        this.id_alumno = id_alumno;
    }

    public String getCodigo_alumno() {
        return codigo_alumno;
    }

    public void setCodigo_alumno(String codigo_alumno) {
        this.codigo_alumno = codigo_alumno;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getFecha_nacimiento() {
        return fecha_nacimiento;
    }

    public void setFecha_nacimiento(String fecha_nacimiento) {
        this.fecha_nacimiento = fecha_nacimiento;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
}
