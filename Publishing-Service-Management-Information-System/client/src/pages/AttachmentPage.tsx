import {
    Box,
    Button,
    Card,
    CardContent,
    Container,
    FormControl,
    InputLabel, MenuItem, Modal,
    Select, SelectChangeEvent,
    TextField,
    Typography
} from "@mui/material";
import Navbar from "../components/Navbar";
import SideMenu from "../components/SideMenu";
import React, {useEffect, useState} from "react";
import NavigationGroup from "../components/NavigationGroup";
import {useParams} from "react-router-dom";
import AttachmentType from "../models/AttachmentType";
import axios from "axios";
import ApiUrl from "../config/api.config";

const style = {
    position: 'absolute' as 'absolute',
    top: '50%',
    left: '50%',
    transform: 'translate(-50%, -50%)',
    width: 400,
    bgcolor: 'background.paper',
    border: '2px solid #000',
    boxShadow: 24,
    p: 4,
};

const AttachmentPage = () => {
    const {id} = useParams()

    useEffect(() => {
        axios.get<AttachmentType[]>(ApiUrl() + "type/attachments").then(response => setTypeSelection(response.data))
    }, [])

    const [open, setOpen] = useState<boolean>(false);

    const handleOpen = () => {
        setOpen(true);
    }
    const handleClose = () => {
        setOpen(false);
    }

    const [typeSelection, setTypeSelection] = useState<AttachmentType[]>([])
    const [selectedType, setSelectedType] = useState<string>("Manuscript");
    const [file, setFile] = useState<File>();

    const handleTypeChange = (event: SelectChangeEvent) => {
        setSelectedType(event.target.value as string);
    }

    const handleFileChange = (event: React.ChangeEvent<HTMLInputElement>) => {
        const fileList = event.target.files;
        if (!fileList) {
            return
        }
        setFile(fileList[0]);
    }

    const handleUpload = () => {
        if (file) {
            const attachmentDto = "{\"publicationId\":" + id + "," + "\"attachmentType\":" + "\"" + selectedType + "\"" + "}";
            const formData = new FormData();
            formData.append("attachmentDTO", attachmentDto);
            formData.append("file", file, file.name);
            axios.post(ApiUrl() + "attachment", formData)
            handleClose()
        }
    }


    return(
        <>
            <Navbar/>
            <SideMenu/>
            <Modal open = {open} onClose = {handleClose}>
                <Box sx = {style}>
                    <Typography variant = "h3">New attachment</Typography>
                    <FormControl fullWidth margin="normal">
                        <InputLabel>Attachment type</InputLabel>
                        <Select onChange = {handleTypeChange} value = {selectedType}>
                            {
                                typeSelection.map(t => {
                                    return <MenuItem value = {t.type}> {t.type} </MenuItem>
                                })
                            }
                        </Select>
                    </FormControl>
                    <input onChange={event => handleFileChange(event)} type="file" multiple ={false}/>
                    <Button onClick ={handleUpload}>Upload</Button>
                </Box>
            </Modal>
            <Container>
                <NavigationGroup id = {id}/>
                <Card>
                    <Button onClick = {handleOpen}>Upload attachment</Button>
                    <CardContent>
                    </CardContent>
                </Card>
            </Container>
        </>
    )
}

export default AttachmentPage