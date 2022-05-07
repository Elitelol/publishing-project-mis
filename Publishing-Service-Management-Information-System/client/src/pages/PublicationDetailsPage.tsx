import {useNavigate, useParams} from "react-router-dom";
import React, {useContext, useEffect, useState} from "react";
import Publication from "../models/Publication";
import axios, {AxiosResponse} from "axios";
import ApiUrl from "../config/api.config";
import {
    Box,
    Button,
    Card,
    CardContent,
    Container,
    FormControl,
    InputLabel,
    MenuItem, Modal,
    Select, SelectChangeEvent,
    TextField,
    Typography
} from "@mui/material";
import Genre from "../models/Genre";
import Language from "../models/Language";
import Progress from "../models/Progress";
import PublicationType from "../models/PublicationType";
import NavigationGroup from "../components/NavigationGroup";
import User from "../models/User";
import Navbar from "../components/Navbar";
import SideMenu from "../components/SideMenu";
import {UserContext} from "../auth";
import {toast} from "react-toastify";

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

const PublicationDetailsPage = () => {

    const {id} = useParams();
    const navigate = useNavigate();
    const [context, setContext] = useContext(UserContext);

    const [publication, setPublication] = useState<Publication>({
        attachments: [],
        authors: [],
        budget: null,
        contract: null,
        genre: "",
        isbn: "",
        language: "",
        manager: null,
        name: "",
        pageNumber: null,
        price: null,
        progressStatus: "",
        progressPercent: 0,
        publicationId: null,
        publicationType: "",
        publishDate: null,
        rejectionReason: "",
        tasks: []
    })

    const [types, setTypes] = useState<PublicationType[]>([]);
    const [progresses, setProgress] = useState<Progress[]>([]);
    const [genres, setGenres] = useState<Genre[]>([]);
    const [languages, setLanguages] = useState<Language[]>([]);
    const [open, setOpen] = useState<boolean>(false);

    useEffect(() => {
        const typeUrl = "type/";
        if (id !== "new") {
            axios.get(ApiUrl() + "publication/" + id).then(response => {
                handleStateChange(response)
            });
        }
        axios.get(ApiUrl() + typeUrl + "publications").then(response => {
            setTypes(response.data)
        })
        axios.get(ApiUrl() + typeUrl + "progress").then(response => {
            setProgress(response.data)
        })
        axios.get(ApiUrl() + typeUrl + "genres").then(response => {
            setGenres(response.data)
        })
        axios.get(ApiUrl() + typeUrl + "languages").then(response => {
            setLanguages(response.data)
        })
    }, [])

    const [publicationId, setPublicationId] = useState<number | null>(publication?.publicationId);
    const [authors, setAuthors] = useState<User[]>(publication?.authors)
    const [name, setName] = useState<string>(publication.name);
    const [publicationType, setPublicationType] = useState<string>(publication?.publicationType);
    const [progressStatus, setProgressStatus] = useState<string>(publication?.progressStatus);
    const [rejectionReason, setRejectionReason] = useState<string>(publication?.rejectionReason);
    const [isbn, setIsbn] = useState<string>(publication?.isbn);
    const [pageNumber, setPageNumber] = useState<number | null>(publication?.pageNumber);
    const [language, setLanguage] = useState<string>(publication?.language);
    const [genre, setGenre] = useState<string>(publication?.genre);
    const [price, setPrice] = useState<number | null>(publication?.price);
    const [manager, setManager] = useState<User | null>(publication?.manager);
    const [rejectionText, setRejectionText] = useState<string>("");

    const handleStateChange = (response: AxiosResponse<any, any>) => {
        setPublication(response.data)
        setPublicationId(response.data.publicationId)
        setAuthors(response.data.authors)
        setName(response.data.name)
        setPublicationType(response.data.publicationType)
        setRejectionReason(response.data.rejectionReason)
        setIsbn(response.data.isbn)
        setPageNumber(response.data.pageNumber)
        setPrice(response.data.price)
        setGenre(response.data.genre)
        setLanguage(response.data.language)
        setManager(response.data.manager)
        setProgressStatus(response.data.progressStatus)
    }

    const handleOpen = () => {
        setOpen(true);
    }
    const handleClose = () => {
        setOpen(false);
    }

    const handleDelete = () => {
    }

    const handleSave = () => {
        axios.post<Publication>(ApiUrl() + "publication", {
            publicationId,
            authors,
            name,
            publicationType,
            progressStatus,
            rejectionReason,
            isbn,
            pageNumber,
            language,
            genre,
            price,
            manager
        }).then(response => handleStateChange(response))
    }

    const handleSubmit = () => {
        axios.post<Publication>(ApiUrl() + "publication/" + id + "/submit").then(response => setPublication(response.data))
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

    const handleProgressChange = (event: SelectChangeEvent) => {
        setProgressStatus(event.target.value as string);
    }

    const handleAssign = () => {
        axios.post<Publication>(ApiUrl() + "publication/assign/?publicationId=" + id + "&managerId=" + context.data?.id).then(response => handleStateChange(response));
        handleDisabled()
    }

    const handleAccept = () => {
        axios.post<Publication>(ApiUrl() + "publication/changeStatus", {
            managerId:context.data?.id,
            publicationId:id,
            status:"Accepted",
            rejectionReason: null
        }).then(response => handleStateChange(response));
        handleDisabled()
    }

    const handleReject = () => {
        axios.post<Publication>(ApiUrl() + "publication/changeStatus", {
            managerId:context.data?.id,
            publicationId:id,
            status:"Rejected",
            rejectionReason: rejectionText
        }).then(response => handleStateChange(response))
        handleClose()
        handleDisabled()
    }

    const [disabled, setDisabled] = useState<boolean>(false);

    const handleDisabled = () => {
        setDisabled(true);
    }

    return (
        <>
            <Navbar/>
            <SideMenu/>
            <Container>
                <Modal open = {open} onClose = {handleClose}>
                    <Box sx = {style}>
                        <Typography variant = "h3">Reject the publication</Typography>
                        <TextField fullWidth margin = "normal" value={rejectionText} onChange = {event => setRejectionText(event.target.value)}/>
                        <Button onClick = {handleReject}>Reject</Button>
                    </Box>
                </Modal>
                <NavigationGroup id = {id} pubStatus = {publication.progressStatus}/>
                <Card>
                    <Typography variant = "h2">Publication details</Typography>
                    <CardContent>
                        <TextField disabled fullWidth label="Publication id" margin = "normal" value={publication.publicationId} onChange = {event => setPublicationId(parseInt(event.target.value))}/>
                        <TextField disabled = {disabled} fullWidth label="Publication name" margin = "normal" value={name} onChange = {event => setName(event.target.value)}/>
                        <TextField disabled = {disabled} fullWidth label = "Publication authors" margin = "normal" value = {
                            publication.authors.map(user => {
                                return user.firstName + " " + user.lastName;
                            })
                        }/>
                        <FormControl fullWidth margin = "normal">
                            <InputLabel>Publication Type</InputLabel>
                            <Select onChange = {handleTypeChange} value = {publicationType}>
                                {
                                    types.map(t => {
                                        return <MenuItem value = {t.type}> {t.type} </MenuItem>
                                    })
                                }
                            </Select>
                        </FormControl>
                        <TextField disabled fullWidth label="Progress status" value ={progressStatus}/>
                        <TextField disabled fullWidth label="Publication rejection reason" margin = "normal" value={publication.rejectionReason} onChange = {event => setRejectionReason(event.target.value)}/>
                        <TextField fullWidth label="Publication ISBN" margin = "normal" value={publication.isbn} onChange = {event => setIsbn(event.target.value)}/>
                        <TextField disabled fullWidth label="Publication page number" margin = "normal" value={publication.pageNumber} onChange = {event => setPageNumber(parseInt(event.target.value))}/>
                        <FormControl disabled = {disabled} fullWidth margin = "normal">
                            <InputLabel>Publication language</InputLabel>
                            <Select onChange = {handleLanguageChange} value = {language}>
                                {
                                    languages.map(t => {
                                        return <MenuItem value = {t.language}> {t.language} </MenuItem>
                                    })
                                }
                            </Select>
                        </FormControl>
                        <FormControl disabled = {disabled} fullWidth margin = "normal">
                            <InputLabel>Publication genre</InputLabel>
                            <Select onChange = {handleGenreChange} value = {genre}>
                                {
                                    genres.map(t => {
                                        return <MenuItem value = {t.genre}> {t.genre} </MenuItem>
                                    })
                                }
                            </Select>
                        </FormControl>
                        <TextField type="number" disabled fullWidth label="Publication price" margin = "normal" value={publication.price} onChange = {event => setPrice(parseFloat(event.target.value))}/>
                        <TextField disabled fullWidth label="Publication manager" margin = "normal" value={publication.manager?.firstName + " " + publication.manager?.lastName}/>
                        <Button onClick = {handleSave}>Save</Button>
                        {publication.progressStatus === "Not Submitted" ?
                            <Button onClick ={handleSubmit}>
                                Submit
                            </Button>
                            : ""
                        }
                        {
                            publication.manager === null && context.data?.role === "Publication Manager" && <Button onClick = {handleAssign}>Assign yourself</Button>
                        }
                        {
                            publication.progressStatus === "In Review" && publication.manager !== null
                            && publication.manager.id === context.data?.id &&
                            <Button onClick = {handleAccept}> Accept </Button>
                        }
                        {
                            publication.progressStatus === "In Review" && publication.manager !== null
                            && publication.manager.id === context.data?.id &&
                            <Button onClick = {handleOpen}> Reject </Button>
                        }
                    </CardContent>
                </Card>
            </Container>
        </>
    )
}

export default PublicationDetailsPage;