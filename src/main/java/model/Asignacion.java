package model;

public class Asignacion {
    private int id_asignacion;
    private String  nombre_alumno;
    private String nombre_empresa;
    private String nombre_tutor;
    private String fecha_inicio;
    private String fecha_fin;

    public int getId_asignacion() {
        return id_asignacion;
    }

    public void setId_asignacion(int id_asignacion) {
        this.id_asignacion = id_asignacion;
    }

    public String getNombre_alumno() {
        return nombre_alumno;
    }

    public void setNombre_alumno(String nombre_alumno) {
        this.nombre_alumno = nombre_alumno;
    }

    public String getNombre_empresa() {
        return nombre_empresa;
    }

    public void setNombre_empresa(String nombre_empresa) {
        this.nombre_empresa = nombre_empresa;
    }

    public String getNombre_tutor() {
        return nombre_tutor;
    }

    public void setNombre_tutor(String nombre_tutor) {
        this.nombre_tutor = nombre_tutor;
    }

    public String getFecha_inicio() {
        return fecha_inicio;
    }

    public void setFecha_inicio(String fecha_inicio) {
        this.fecha_inicio = fecha_inicio;
    }

    public String getFecha_fin() {
        return fecha_fin;
    }

    public void setFecha_fin(String fecha_fin) {
        this.fecha_fin = fecha_fin;
    }

    public Asignacion(int id_asignacion, String nombre_alumno, String nombre_empresa, String nombre_tutor, String fecha_inicio, String fecha_fin) {
        this.id_asignacion = id_asignacion;
        this.nombre_alumno = nombre_alumno;
        this.nombre_empresa = nombre_empresa;
        this.nombre_tutor = nombre_tutor;
        this.fecha_inicio = fecha_inicio;
        this.fecha_fin = fecha_fin;
    }

    public Asignacion() {
    }
}
