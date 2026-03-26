
package controlador;
import modelo_Dao.UsuarioDaoImpl;
import java.io.IOException;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import modelo_Dao.UsuarioDao;
import modelo.Usuario;

public class Login extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
            String accion = request.getParameter("accion");
            
            switch(accion){
                case "login":
                    try {
                        String usu = request.getParameter("usuario");
                        String con = request.getParameter("contraseña");
                        UsuarioDao udao = new UsuarioDaoImpl();
                        //Busco el Usuario y la contraseña
                        Usuario u = udao.buscarUsuario(usu,con);
                        
                        //Si lo encuentro creo la sesión y añado el idUsuario y su perfil 
                        if(u.getNomUSuario()!=null){
                            HttpSession sesion = request.getSession();
                            sesion.setAttribute("idUsuario", u.getIdUsuario());
                            sesion.setAttribute("perfil", u.getPerfil());
                           request.getRequestDispatcher("index.jsp").forward(request, response);
                        }else{
                            request.setAttribute("mensaje", "El Usuario o la Contraseña es incorrecta");
                            request.getRequestDispatcher("WEB-INF/páginas/mensajeErrorLogin.jsp").forward(request, response);
                        }

                    } catch (SQLException e) {
                        request.setAttribute("mensaje", "Ocurrió un problema al iniciar sesión, vuelva a intentar.");
                        request.getRequestDispatcher("WEB-INF/páginas/mensajeErrorLogin.jsp").forward(request, response);
                    }
                    break;  
                case "cerrar":
                    //Cierro la sesión 
                    HttpSession sesion = request.getSession();
                    sesion.removeAttribute("idUsuario");
                    sesion.removeAttribute("perfil");
                    sesion.invalidate();
                    request.getRequestDispatcher("index.jsp").forward(request, response);
                    
            }
    }

    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method. 
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}