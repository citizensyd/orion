import com.openclassroom.orion.module.article.dto.CommentDTO;
import com.openclassroom.orion.module.article.service.CommentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/articles/{articleId}/comments")
public class CommentController {

    private final CommentService commentService;

    @Autowired
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping
    @Operation(summary = "Ajouter un commentaire à un article",
            description = "Permet d'ajouter un commentaire à un article spécifique en utilisant l'ID de l'article.",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Commentaire créé avec succès"),
                    @ApiResponse(responseCode = "400", description = "Informations fournies invalides"),
                    @ApiResponse(responseCode = "404", description = "Article non trouvé")
            })
    public ResponseEntity<CommentDTO> addCommentToArticle(@PathVariable Long articleId, @RequestBody CommentDTO commentDTO) {
        CommentDTO savedCommentDTO = commentService.addCommentToArticle(articleId, commentDTO);
        return new ResponseEntity<>(savedCommentDTO, HttpStatus.CREATED);
    }
}
