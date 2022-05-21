import UserComment from "../models/UserComment";
import {Button, Divider, ListItem, ListItemButton, ListItemText, TextField, Typography} from "@mui/material";
import React, {useContext, useEffect, useState} from "react";
import axios from "axios";
import ApiUrl from "../config/api.config";
import User from "../models/User";
import {UserContext} from "../auth";

type CommentProp = {
    comment: UserComment
    comments: UserComment[]
    url: string
    entityId: number | null
    setComments: React.Dispatch<React.SetStateAction<UserComment[]>>
    commentator: User
}

const Comment = ({comment, comments, url, entityId, setComments, commentator}: CommentProp) => {

    const [replyText, setText] = useState<string>("");
    const [showReply, setShowReply] = useState<boolean>(false);
    const [context, setContext] = useContext(UserContext);

    const handleReplyShow = () => {
        setShowReply(!showReply);
    }

    const handleReply = () => {
        axios.post<any>(ApiUrl() + url + "/comment", {
            commentId: null,
            user: {
                id: commentator.id,
                username: commentator.username,
                firstName: commentator.firstName,
                lastName: commentator.lastName,
                email: commentator.email,
                role: commentator.role
            },
            entityId,
            text: replyText,
            rootCommentId: comment.commentId,
            replies: []
        }).then(response => setComments(response.data.comments));
        handleReplyShow();
    }

    const handleDelete = (id: any) => {
        axios.delete(ApiUrl() + url + "/deleteComment?id=" + id).then(() => setComments(comments.filter(comment => comment.commentId !== id)))
    }

    return (
        <>
            <ListItem>
                <ListItemText
                    primary={
                        <>
                            <Typography>
                                {comment.user.username + " (" + comment.user.firstName + " " + comment.user.lastName + ") Posted at " + comment.posted }
                            </Typography>
                        </>
                    }
                    secondary={
                        <div>
                            <Typography>
                                {comment.text}
                                <ListItemButton onClick ={handleReplyShow}>Reply</ListItemButton>
                                {
                                    context.data?.id === comment.user.id && <ListItemButton onClick ={() => handleDelete(comment.commentId)}>Delete</ListItemButton>
                                }
                            </Typography>
                            {
                                comment.replies.length > 0 ? comment.replies.map((reply: any) => {
                                    return <Comment comment={reply} comments={comments} url={url} entityId={entityId} setComments={setComments} commentator={commentator} />
                                }) : ""
                            }
                        </div>
                    }>
                </ListItemText>
            </ListItem>
            {
                showReply && <div>
                    <TextField fullWidth margin = "normal" value={replyText} onChange = {event => setText(event.target.value)}> TEST</TextField>
                    {
                        replyText.length > 0 && <Button onClick = {handleReply}>Reply</Button>
                    }
                    <Button onClick = {handleReplyShow}>Close</Button>
                </div>
            }
            <Divider light />
        </>
    )
}

export default Comment