import {Button, Card, CardContent, List, ListItemButton, TextField, Typography} from "@mui/material";
import UserComment from "../models/UserComment";
import Comment from "./Comment";
import React, {useContext, useEffect, useState} from "react";
import axios from "axios";
import ApiUrl from "../config/api.config";
import User from "../models/User";

type CommentProps = {
    comments: UserComment[]
    url: string
    entityId: number | null
    setComments: React.Dispatch<React.SetStateAction<UserComment[]>>
    commentator: User
}

const Comments = ( {comments, url, entityId, setComments, commentator} : CommentProps ) => {

    const [text, setText] = useState<string>("");

    const handleSave = () => {
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
            text,
            rootCommentId: null,
            replies: []
        }).then(response => setComments(response.data.comments));
        setText("")
    }

    return(
        <Card>
            <Typography variant="h3"> Comments </Typography>
            <Card>
                <Typography variant = "h4"> Add comment</Typography>
                <TextField fullWidth margin="normal" label ="Text" value={text} onChange={event => setText(event.target.value)}/>
                {
                    text.length > 0 && <Button onClick = {handleSave}> Post </Button>
                }
                <CardContent>
                    <List>
                        {
                            comments.map(value => {
                                return <Comment comment={value} comments={comments} url={url} entityId = {entityId} setComments={setComments} commentator={commentator}/>
                            })
                        }
                    </List>
                </CardContent>
            </Card>
        </Card>
    )
}

export default Comments