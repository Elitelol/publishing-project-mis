import Attachment from "../models/Attachment";
import {Button, TableCell, TableRow} from "@mui/material";
import axios from "axios";
import ApiUrl from "../config/api.config";
import {useNavigate, useParams} from "react-router-dom";
import React from "react";

type Props = {
   value: Attachment | any
   attachments: Attachment[]
    setAttachments: React.Dispatch<React.SetStateAction<Attachment[]>>
}

const AttachmentRow = ({value, attachments, setAttachments}: Props) => {

    const handleDownload = () => {
        axios.get(ApiUrl() + "attachment/" + value.attachmentId, {responseType: "blob"}).then(response => {
            const url = window.URL.createObjectURL(new Blob([response.data]));
            const link = document.createElement('a');
            link.href = url;
            link.download = value.fileName
            link.click()
        })
    }

    const handleDelete = () => {
        axios.delete(ApiUrl() + "attachment/" + value.attachmentId).then(() => {
            setAttachments(attachments.filter(attachment => attachment.attachmentId !== value.attachmentId))
        })
    }

    return(
        <TableRow key={value.attachmentId} sx={{ '&:last-child td, &:last-child th': { border: 0 } }}>
            <TableCell align="right">{value.fileName}</TableCell>
            <TableCell align="right">{value.attachmentType}</TableCell>
            <TableCell align="right">{value.contentType}</TableCell>
            <TableCell align="right">
                <Button variant = "text" onClick ={handleDownload}>
                    Download
                </Button>
                <Button variant = "text" onClick ={handleDelete}>
                    Delete
                </Button>
            </TableCell>
        </TableRow>
    )
}

export default AttachmentRow