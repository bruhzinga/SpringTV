import {useRef} from "react";
import moviesService from "../services/MoviesService.js";
import AuthenticationService from "../services/UserService.js";

export function Comments({setComments, comments, id, newCommentAddedHandler}) {


    const inputRef = useRef();

    function addCommentHandler() {

        const comment = inputRef.current.value;
        console.log(comment);

        AuthenticationService.postComment(id,comment).then((response) => {
            newCommentAddedHandler();
        }).catch((error) => {
            alert(error.response.data);
        });
    }

    return (
        <div>
            <h1>Comments</h1>


            <h2>Post Comment</h2>
            <form action="">
                <input className="write_comment" ref={inputRef} type="text" placeholder="Write coment"/>
                <input className="post_comment" type="button" value="Post Comment" onClick={addCommentHandler}/>
            </form>

            <div>
                <ul className="commentList">
                    {comments && comments.map((comment) => (
                        <li className="comment" key={comment.id + "comment"}>
                            <div className="commentAuthor">{comment.username}</div>
                            <div className="commentText">{comment.comment}</div>

                        </li>
                    ))}
                </ul>
            </div>
        </div>
    )
}