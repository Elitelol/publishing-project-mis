import Publication from "../models/Publication";
import {useNavigate} from "react-router-dom";
import {
    Box,
    Button, FormControl, InputLabel, MenuItem, Modal,
    Paper, Select, SelectChangeEvent,
    Table, TableBody, TableCell,
    TableContainer,
    TableHead, TableRow, TextField,
    Typography
} from "@mui/material";
import PublicationRow from "./PublicationRow";
import {UserContext} from "../auth";
import {useContext, useEffect, useState} from "react";
import axios from "axios";
import ApiUrl from "../config/api.config";
import Genre from "../models/Genre";
import Language from "../models/Language";
import PublicationType from "../models/PublicationType";
import User from "../models/User"

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

type Props = {
    publications: Publication[],
    publicationText: string
}

const Publications = ({publications, publicationText}: Props) => {
    const [open, setOpen] = useState<boolean>(false);
    const navigate = useNavigate();
    const [context, setContext] = useContext(UserContext);
    const [types, setTypes] = useState<PublicationType[]>([]);
    const [genres, setGenres] = useState<Genre[]>([]);
    const [languages, setLanguages] = useState<Language[]>([]);
    const [language, setLanguage] = useState<string>("English");
    const [genre, setGenre] = useState<string>("Science Fiction");
    const [publicationType, setPublicationType] = useState<string>("Book");
    const [name, setName] = useState<string>("");
    const [user, setUser] = useState<User>({email: "", firstName: "", id: 0, lastName: "", role: "", username: ""});

    useEffect(() => {
        const typeUrl = "type/";
        axios.get(ApiUrl() + typeUrl + "publications").then(response => {
            setTypes(response.data)
        })
        axios.get(ApiUrl() + typeUrl + "genres").then(response => {
            setGenres(response.data)
        })
        axios.get(ApiUrl() + typeUrl + "languages").then(response => {
            setLanguages(response.data)
        })
        axios.get(ApiUrl() + "user/" + context.data?.id).then(response => setUser(response.data));
    }, [])

    const handleOpen = () => {
        setOpen(true);
    }
    const handleClose = () => {
        setOpen(false);
    }

    const handleLanguageChange = (event: SelectChangeEvent) => {
        setLanguage(event.target.value as string);
    }

    const handleGenreChange = (event: SelectChangeEvent) => {
        setGenre(event.target.value as string);
    }

    const handleTypeChange = (event: SelectChangeEvent) => {
        setPublicationType(event.target.value as string);
    }

    const handleSave = () => {
        axios.post<Publication>(ApiUrl() + "publication", {
            publicationId: null,
            name,
            publicationType,
            authors: [user],
            progressStatus:"Not Started",
            progressPercent: 0,
            isbn: null,
            language,
            genre,
            pageNumber: null,
            rejectionReason: null
        }).then(response => {
            navigate("/publication/" + response.data.publicationId)
        })
    }

    return (
        <>
            <Typography variant = "h2" >{publicationText}</Typography>
            {
                context.data?.role === "Author" && publicationText === "My publication works" ? <Button onClick = {handleOpen}>Add new publication</Button> : ""
            }
            <Modal open = {open} onClose = {handleClose}>
                <Box sx = {style}>
                    <Typography variant = "h3">New publication</Typography>
                    <TextField fullWidth label="Publication name" margin = "normal" value={name} onChange = {event => setName(event.target.value)}/>
                    <FormControl fullWidth>
                        <InputLabel>Publication Type</InputLabel>
                        <Select onChange = {handleTypeChange} value = {publicationType}>
                            {
                                types.map(t => {
                                    return <MenuItem value = {t.type}> {t.type} </MenuItem>
                                })
                            }
                        </Select>
                    </FormControl>
                    <FormControl fullWidth margin = "normal">
                        <InputLabel>Publication language</InputLabel>
                        <Select onChange = {handleLanguageChange} value = {language}>
                            {
                                languages.map(t => {
                                    return <MenuItem value = {t.language}> {t.language} </MenuItem>
                                })
                            }
                        </Select>
                    </FormControl>
                    <FormControl fullWidth margin = "normal">
                        <InputLabel>Publication genre</InputLabel>
                        <Select onChange = {handleGenreChange} value = {genre}>
                            {
                                genres.map(t => {
                                    return <MenuItem value = {t.genre}> {t.genre} </MenuItem>
                                })
                            }
                        </Select>
                    </FormControl>
                    <Button onClick ={handleSave}>Create</Button>
                </Box>
            </Modal>
            <TableContainer component = {Paper}>
                <Table sx = {{minWidth: 650}}>
                    <TableHead>
                        <TableRow>
                            <TableCell align ="right">Publication Id</TableCell>
                            <TableCell align ="right">Publication name</TableCell>
                            <TableCell align ="right">Publication type</TableCell>
                            <TableCell align ="right">Genre</TableCell>
                            <TableCell align ="right">Author</TableCell>
                            <TableCell align ="right">Progress status</TableCell>
                            <TableCell align ="right">Progress</TableCell>
                            <TableCell align ="right">Manager</TableCell>
                        </TableRow>
                    </TableHead>
                    <TableBody>
                        {publications.map((value: Publication) => (
                            <PublicationRow key={value.publicationId} value = {value}/>
                        ))}
                    </TableBody>
                </Table>
            </TableContainer>
        </>
    )
}

export default Publications