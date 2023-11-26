package com.adrmeneses.evalab_resultanalisisclinicos.usuarios;
//Clase que actúa como un contenedor global para el ID del usuario
//Esta clase implementa el patrón singleton para asegurarse de que solo haya una instancia en toda la aplicación
public class UsuarioActivo {
        private static UsuarioActivo instance;
        private long idUsuario;

        private UsuarioActivo() {}

        public static synchronized UsuarioActivo getInstance() {
            if (instance == null) {
                instance = new UsuarioActivo();
            }
            return instance;
        }

        public void setIdUsuario(long idUsuario) {
            this.idUsuario = idUsuario;
        }

        public long getIdUsuario() {
            return idUsuario;
        }

}
