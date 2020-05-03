package br.com.bruno_martins.padrao.rest.services;

import br.com.bruno_martins.padrao.rest.models.Usuario;
import br.com.bruno_martins.padrao.rest.models.UsuarioLogado;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Bruno Martins
 */
@Transactional
public class SessionService {
    
    private final Map<String, UsuarioLogado> usuariosLogados = new HashMap();
    
    public SessionService() {
        Usuario usuarioFixo = new Usuario();
        usuarioFixo.setId(-1L);
        
        usuarioFixo.setLogin("admin");
        usuarioFixo.setTempoSessao(0);
        UsuarioLogado usuarioFixoLogado = new UsuarioLogado("I8794@&8jHGGFgh%_", usuarioFixo);
        usuarioFixoLogado.setDataValidade(new Date(2050 + 1900, 1, 1));
        
        usuariosLogados.put("I8794@&8jHGGFgh%_", usuarioFixoLogado);
    }
    
    public UsuarioLogado add(String sessionId, Usuario usuario) {
        
        boolean usuarioJaExiste = false;
        String sessionIdOld = null;
        for (Map.Entry<String, UsuarioLogado> entry : usuariosLogados.entrySet()) {
            if (Objects.equals(usuario.getId(), entry.getValue().getUsuarioId())) {
                usuarioJaExiste = true;
                sessionIdOld = entry.getKey();
                break;
            }
        }
        
        if (usuarioJaExiste) {
            usuariosLogados.remove(sessionIdOld);
        }
        
        UsuarioLogado usuarioLogado = new UsuarioLogado(sessionId, usuario);
        usuariosLogados.put(sessionId, usuarioLogado);
        
        System.out.println("Data de expiração: " + usuarioLogado.getDataValidade());
        
        return usuarioLogado;
        
    }
    
    @Scheduled(initialDelay = 0, fixedDelay = 600000)
    public void cleanSessions() {
        System.out.println(new Date() + " Limpando sessões...");
        for (Map.Entry<String, UsuarioLogado> entry : usuariosLogados.entrySet()) {
            if (!entry.getValue().isValido()) {
                usuariosLogados.remove(entry.getKey());
            }
        }
        System.out.println("Qtd de sessões: " + usuariosLogados.size());
    }
    
    public boolean refreshSessionTimeout(String sessionId) {
        UsuarioLogado usuario = usuariosLogados.get(sessionId);
        if (usuario == null || !usuario.isValido()) {
            usuariosLogados.remove(sessionId);
            return false;
        }
        if (!usuario.getSessionId().equals("I8794@&8jHGGFgh%_")) {
            usuario.atualizarValidade();
        }
        
        return true;
    }
    
    public UsuarioLogado getUsuarioLogado(String sessionId) {
        return usuariosLogados.get(sessionId);
    }
    
}
