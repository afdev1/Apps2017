package afdev.unal.edu.co.webservices;

public class WifiZone {
    private String nombre;
    private String municipio;
    private String region;
    private String latitud;
    private String longitud;
    private String direccion;

    public WifiZone(String nombre, String municipio, String latitud, String longitud, String region, String direccion) {
        this.nombre = nombre;
        this.municipio = municipio;
        this.region = region;
        this.latitud = latitud;
        this.longitud = longitud;
        this.direccion = direccion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getMunicipio() {
        return municipio;
    }

    public void setMunicipio(String municipio) {
        this.municipio = municipio;
    }

    public String getLatitud() {
        return latitud;
    }

    public void setLatitud(String latitud) {
        this.latitud = latitud;
    }

    public String getLongitud() {
        return longitud;
    }

    public void setLongitud(String longitud) {
        this.longitud = longitud;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }
}
