import {
    AlertColor,
    Box,
    Button,
    Card,
    CardContent,
    Container,
    FormControl,
    InputLabel, MenuItem, Modal, Paper,
    Select, SelectChangeEvent, Table, TableBody, TableCell, TableContainer, TableHead, TableRow,
    TextField,
    Typography
} from "@mui/material";
import React, {useEffect, useState} from "react";
import NavigationGroup from "../components/NavigationGroup";
import {useParams} from "react-router-dom";
import AttachmentType from "../models/AttachmentType";
import axios from "axios";
import ApiUrl from "../config/api.config";
import Attachment from "../models/Attachment";
import AttachmentRow from "../components/AttachmentRow";
import NavMenu from "../components/NavMenu";
import AlertMessage from "../components/AlertMessage";
import Publication from "../models/Publication";

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
        //awful
        axios.get<Publication>(ApiUrl() + "publication/" + id).then(response => {
            setDisabled(response.data.progressStatus === "Rejected" || response.data.progressStatus === "Not Submitted"
                || response.data.progressStatus === "Not Started" || response.data.progressStatus === "In Review")
        })
        fetchManuscript()
        fetchCover()
        fetchContract()
        fetchTask()
    }, [])

    const [open, setOpen] = useState<boolean>(false);

    const fetchManuscript = () => {
        axios.get<Attachment[]>(ApiUrl() + "attachment/" + id + "/manuscript").then(response => setManuscripts(response.data))
    }

    const fetchContract = () => {
        axios.get<Attachment[]>(ApiUrl() + "attachment/" + id + "/contract").then(response => setContracts(response.data))
    }

    const fetchTask = () => {
        axios.get<Attachment[]>(ApiUrl() + "attachment/" + id + "/task").then(response => setTasks(response.data))
    }

    const fetchCover = () => {
        axios.get<Attachment[]>(ApiUrl() + "attachment/" + id + "/cover").then(response => setCovers(response.data))
    }

    const handleOpen = () => {
        setOpen(true);
    }
    const handleClose = () => {
        setOpen(false);
    }

    const [disabled, setDisabled] = useState<boolean>(true);
    const [typeSelection, setTypeSelection] = useState<AttachmentType[]>([])
    const [selectedType, setSelectedType] = useState<string>("Manuscript");
    const [file, setFile] = useState<File>();
    const [showManuscript, setShowManuscript] = useState<boolean>(true);
    const [showTask, setShowTask] = useState<boolean>(false);
    const [showContract, setShowContract] = useState<boolean>(false);
    const [showCover, setShowCover] = useState<boolean>(false);
    const [manuscripts, setManuscripts] = useState<Attachment[]>([]);
    const [tasks, setTasks] = useState<Attachment[]>([]);
    const [contracts, setContracts] = useState<Attachment[]>([]);
    const [covers, setCovers] = useState<Attachment[]>([])
    const[showMessage, setShowMessage] = useState<boolean>(false);
    const [message, setMessage] = useState<string[]>([]);
    const [severity, setSeverity] = useState<AlertColor | undefined>("success");

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
            axios.post(ApiUrl() + "attachment", formData).then(() => {
                fetchManuscript()
                fetchCover()
                fetchContract()
                fetchTask()
            }).then(() => {
                setMessage(["Attachment uploaded."]);
                setShowMessage(true);
                setSeverity("success");
                handleClose()
            }).catch(error => {
                setMessage(error.response.data.message)
                setShowMessage(true);
                setSeverity("error");
            })
        }
    }

    const handleManuscript = () => {
        setShowManuscript(true);
        setShowTask(false);
        setShowContract(false);
        setShowCover(false);
    }

    const handleTask = () => {
        setShowManuscript(false);
        setShowTask(true);
        setShowContract(false);
        setShowCover(false);
    }

    const handleContract = () => {
        setShowManuscript(false);
        setShowTask(false);
        setShowContract(true);
        setShowCover(false);
    }

    const handleCover = () => {
        setShowManuscript(false);
        setShowTask(false);
        setShowContract(false);
        setShowCover(true);
    }


    return(
        <>
            <NavMenu/>
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
                <NavigationGroup id = {id} unallowedToClick={disabled} unallowedAttach={false}/>
                <Typography variant = "h2">Publication attachments</Typography>
                {
                    showMessage && <AlertMessage severity={severity} message={message} setShowMessage={setShowMessage}/>
                }
                <Button onClick={handleManuscript}>Manuscripts</Button>
                <Button onClick={handleTask}>Tasks</Button>
                <Button onClick={handleContract}>Contract</Button>
                <Button onClick={handleCover}>Cover</Button>
                <Card>
                    <Button onClick = {handleOpen}>Upload attachment</Button>
                    <CardContent>
                    </CardContent>
                </Card>
                <TableContainer component = {Paper}>
                    <Table sx = {{minWidth: 650}}>
                        <TableHead>
                            <TableRow>
                                <TableCell align ="right">Attachment name</TableCell>
                                <TableCell align ="right">Attachment type</TableCell>
                                <TableCell align ="right">Content type</TableCell>
                            </TableRow>
                        </TableHead>
                        <TableBody>
                            {
                                showManuscript && manuscripts.map(manuscript => {
                                    return <AttachmentRow value = {manuscript} attachments = {manuscripts} setAttachments = {setManuscripts}/>
                                })
                            }
                            {
                                showTask && tasks.map(task => {
                                    return <AttachmentRow value = {task} attachments = {tasks} setAttachments = {setTasks}/>
                                })
                            }
                            {
                                showContract && contracts.map(contract => {
                                    return <AttachmentRow value = {contract} attachments = {contracts} setAttachments = {setContracts}/>
                                })
                            }
                            {
                                showCover && covers.map(cover => {
                                    return <AttachmentRow value = {cover} attachments = {covers} setAttachments = {setCovers}/>
                                })
                            }
                        </TableBody>
                    </Table>
                </TableContainer>
            </Container>
        </>
    )
}

export default AttachmentPage