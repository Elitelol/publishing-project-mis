import {useNavigate, useParams} from "react-router-dom";
import {useContext, useEffect, useState} from "react";
import Publication from "../models/Publication";
import axios from "axios";
import ApiUrl from "../config/api.config";
import {
    Button,
    Card,
    CardContent,
    Container,
    FormControl,
    InputLabel,
    MenuItem,
    Select, SelectChangeEvent,
    TextField,
    Typography
} from "@mui/material";
import Genre from "../models/Genre";
import Language from "../models/Language";
import Progress from "../models/Progress";
import PublicationType from "../models/PublicationType";
import NavigationGroup from "../components/NavigationGroup";

const PublicationPage = () => {

    const {id} = useParams();
    const navigate = useNavigate();
    const [publication, setPublication] = useState<Publication>({
        attachments: [],
        authorId: [],
        budget: null,
        contract: null,
        genre: "",
        isbn: "",
        language: "",
        managerId: null,
        name: "",
        pageNumber: null,
        price: null,
        progressStatus: "",
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

    useEffect(() => {
        const typeUrl = "type/";
        console.log(id)
        if (id !== "new") {
            axios.get(ApiUrl() + "publication/" + id).then(response => {
                setPublication(response.data)
                setPublicationId(response.data.publicationId)
                setAuthorId(response.data.authorId)
                setName(response.data.name)
                setPublicationType(response.data.publicationType)
                setRejectionReason(response.data.rejectionReason)
                setIsbn(response.data.isbn)
                setPageNumber(response.data.pageNumber)
                setPrice(response.data.price)
                setGenre(response.data.genre)
                setLanguage(response.data.language)
                setManagerId(response.data.managerId)
                setProgressStatus(response.data.progressStatus)
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

    const [publicationId, setPublicationId] = useState(publication?.publicationId);
    const [authorId, setAuthorId] = useState(publication?.authorId)
    const [name, setName] = useState(publication.name);
    const [publicationType, setPublicationType] = useState(publication?.publicationType);
    const [progressStatus, setProgressStatus] = useState(publication?.progressStatus);
    const [rejectionReason, setRejectionReason] = useState(publication?.rejectionReason);
    const [isbn, setIsbn] = useState(publication?.isbn);
    const [pageNumber, setPageNumber] = useState(publication?.pageNumber);
    const [language, setLanguage] = useState(publication?.language);
    const [genre, setGenre] = useState(publication?.genre);
    const [price, setPrice] = useState(publication?.price);
    const [managerId, setManagerId] = useState(publication?.managerId);

    const handleDelete = () => {

    }

    const handleSave = () => {
        axios.post<Publication>(ApiUrl() + "publication", {
            publicationId,
            authorId,
            name,
            publicationType,
            progressStatus,
            rejectionReason,
            isbn,
            pageNumber,
            language,
            genre,
            price,
            managerId
        }).then(response => setPublication(response.data))
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

    return (
        <>
            <Container>
                <NavigationGroup id = {id}/>
                <Card>
                    <Typography variant = "h2">Publication details</Typography>
                    <CardContent>
                        <TextField disabled fullWidth label="Publication id" margin = "normal" value={publication.publicationId} onChange = {event => setPublicationId(parseInt(event.target.value))}/>
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
                        <FormControl fullWidth>
                            <InputLabel>Publication status</InputLabel>
                            <Select onChange = {handleProgressChange} value = {progressStatus}>
                                {
                                    progresses.map(t => {
                                        return <MenuItem value = {t.status}> {t.status} </MenuItem>
                                    })
                                }
                            </Select>
                        </FormControl>
                        <TextField fullWidth label="Publication rejection reason" margin = "normal" value={publication.rejectionReason} onChange = {event => setRejectionReason(event.target.value)}/>
                        <TextField fullWidth label="Publication ISBN" margin = "normal" value={publication.isbn} onChange = {event => setIsbn(event.target.value)}/>
                        <TextField fullWidth label="Publication page number" margin = "normal" value={publication.pageNumber} onChange = {event => setPageNumber(parseInt(event.target.value))}/>
                        <FormControl fullWidth>
                            <InputLabel>Publication language</InputLabel>
                            <Select onChange = {handleLanguageChange} value = {language}>
                                {
                                    languages.map(t => {
                                        return <MenuItem value = {t.language}> {t.language} </MenuItem>
                                    })
                                }
                            </Select>
                        </FormControl>
                        <FormControl fullWidth>
                            <InputLabel>Publication genre</InputLabel>
                            <Select onChange = {handleGenreChange} value = {genre}>
                                {
                                    genres.map(t => {
                                        return <MenuItem value = {t.genre}> {t.genre} </MenuItem>
                                    })
                                }
                            </Select>
                        </FormControl>
                        <TextField fullWidth label="Publication price" margin = "normal" value={publication.price} onChange = {event => setPrice(parseFloat(event.target.value))}/>
                        <TextField fullWidth label="Publication manager" margin = "normal" value={publication.managerId}/>
                        {

                        }
                        <Button onClick = {handleSave}>Save</Button>
                        <Button onClick = {handleDelete}>Delete</Button>
                    </CardContent>
                </Card>
            </Container>
        </>
    )
}

export default PublicationPage;