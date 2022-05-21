import {useParams} from "react-router-dom";
import {
    AlertColor,
    Box,
    Button,
    Card,
    CardContent,
    Container,
    FormControl,
    InputLabel, MenuItem, Modal,
    Select,
    TextField,
    Typography
} from "@mui/material";
import NavigationGroup from "../components/NavigationGroup";
import React, {useContext, useEffect, useState} from "react";
import Contract from "../models/Contract";
import axios, {AxiosResponse} from "axios";
import ApiUrl from "../config/api.config";
import Publication from "../models/Publication";
import Comments from "../components/Comments";
import UserComment from "../models/UserComment";
import User from "../models/User";
import {UserContext} from "../auth";
import Navbar from "../components/Navbar";
import SideMenu from "../components/SideMenu";
import {AdapterDateFns} from "@mui/x-date-pickers/AdapterDateFns";
import {DatePicker} from "@mui/x-date-pickers";
import {LocalizationProvider} from "@mui/x-date-pickers/LocalizationProvider";
import NavMenu from "../components/NavMenu";
import AlertMessage from "../components/AlertMessage";

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

const ContractPage = () => {
    const {id} = useParams();
    const [context, setContext] = useContext(UserContext);

    useEffect( () => {
        try{
            axios.get<User>(ApiUrl() + "user/" + context.data?.id).then(response => setCommentator(response.data))
            axios.get<Contract>(ApiUrl() + "contract/" + id).then(response => handleStateChange(response));
        } catch (error) {

        }
    }, [])

    const [contract, setContract] = useState<Contract>({
        amountOfCompletedManuscript: 0,
        amountOnInitialPublish: 0,
        amountOnSigningContract: 0,
        comments: [],
        contractId: null,
        firstCoverPercent: 0,
        firstCoverRate: 0,
        lastCoverPercent: 0,
        lastCoverRate: 0,
        publicationId: "",
        publicationPrice: 0,
        publishDate: null,
        secondCoverPercent: 0,
        secondCoverRate: 0,
        withinMonthsAfterPublish: 0
    })

    const [amountOfCompletedManuscript, setAmountOfCompletedManuscript] = useState<number>(contract.amountOfCompletedManuscript)
    const [amountOnInitialPublish, setAmountOnInitialPublish] = useState<number>(contract.amountOnInitialPublish)
    const [amountOnSigningContract, setAmountOnSigningContract] = useState<number>(contract.amountOnSigningContract)
    const [firstCoverPercent, setFirstCoverPercent] = useState<number>(contract.firstCoverPercent)
    const [firstCoverRate, setFirstCoverRate] = useState<number>(contract.firstCoverRate)
    const [lastCoverPercent, setLastCoverPercent] = useState<number>(contract.lastCoverPercent)
    const [lastCoverRate, setLastCoverRate] = useState<number>(contract.lastCoverRate)
    const [contractId, setContractId] = useState<number | null>(contract.contractId)
    const [publicationPrice, setPublicationPrice] = useState<number>(contract.publicationPrice)
    const [publishDate, setPublishDate] = useState<Date | null>(contract.publishDate)
    const [secondCoverPercent, setSecondCoverPercent] = useState<number>(contract.secondCoverPercent)
    const [secondCoverRate, setSecondCoverRate] = useState<number>(contract.secondCoverRate)
    const [withinMonthsAfterPublish, setWithinMonthsAfterPublish] = useState<number>(contract.withinMonthsAfterPublish)
    const [contractComments, setComments] = useState<UserComment[]>([]);
    const [commentator, setCommentator] = useState<User>({
        email: "", firstName: "", id: null, lastName: "", role: "", username: ""
    })
    const [disabled, setDisabled] = useState<boolean>(true);
    const [open, setOpen] = useState<boolean>(false);
    const [file, setFile] = useState<File>();
    const[showMessage, setShowMessage] = useState<boolean>(false);
    const [message, setMessage] = useState<string[]>([]);
    const [severity, setSeverity] = useState<AlertColor | undefined>("success");

    const handleOpen = () => {
        setOpen(true);
    }
    const handleClose = () => {
        setOpen(false);
    }

    const handleStateChange = (response: AxiosResponse<any, any>) => {
        setContract(response.data)
        setContractId(response.data.contractId)
        setPublishDate(response.data.publishDate)
        setPublicationPrice(response.data.publicationPrice)
        setAmountOnSigningContract(response.data.amountOnSigningContract)
        setAmountOfCompletedManuscript(response.data.amountOfCompletedManuscript)
        setAmountOnInitialPublish(response.data.amountOnInitialPublish)
        setWithinMonthsAfterPublish(response.data.withinMonthsAfterPublish)
        setFirstCoverRate(response.data.firstCoverRate)
        setFirstCoverPercent(response.data.firstCoverPercent)
        setSecondCoverRate(response.data.secondCoverRate)
        setSecondCoverPercent(response.data.secondCoverPercent)
        setLastCoverRate(response.data.lastCoverRate)
        setLastCoverPercent(response.data.lastCoverPercent)
        setComments(response.data.comments)
        setDisabled(context.data?.role !== "Publication Manager");
    }

    const handleSave = async () => {
       await axios.post<Contract>(ApiUrl() + "contract", {
            publicationId: id,
            contractId,
            publishDate,
            publicationPrice,
            amountOnSigningContract,
            amountOfCompletedManuscript,
            amountOnInitialPublish,
            withinMonthsAfterPublish,
            firstCoverRate,
            firstCoverPercent,
            secondCoverRate,
            secondCoverPercent,
            lastCoverRate,
            lastCoverPercent
        }).then(response => {
            handleStateChange(response)
            setMessage(["Contract saved."]);
            setShowMessage(true);
            setSeverity("success");
        }).catch(error => {
            setMessage(error.response.data.message)
            setShowMessage(true);
            setSeverity("error");
        })
    }

    const handleContractSigned = async () => {
        await axios.post<Publication>(ApiUrl() + "publication/" + id + "/setContract").then(() => {
            setMessage(["Contract signed."]);
            setShowMessage(true);
            setSeverity("success");
        }).catch(error => {
            setMessage(error.response.data.message)
            setShowMessage(true);
            setSeverity("error");
        })
    }

    const handleGenerateContract = async () => {
        await axios.get(ApiUrl() + "contract/" + id + "/downloadContract", {responseType: "blob"}).then(response => {
            const url = window.URL.createObjectURL(new Blob([response.data]));
            const link = document.createElement('a');
            link.href = url;
            link.download = "contract_" + id + ".pdf"
            link.click()
        })
    }

    const handleFileChange = (event: React.ChangeEvent<HTMLInputElement>) => {
        const fileList = event.target.files;
        if (!fileList) {
            return
        }
        setFile(fileList[0]);
    }

    const handleUpload = async () => {
        if (file) {
            const attachmentDto = "{\"publicationId\":" + id + "," + "\"attachmentType\":" + "\"" + "Contract" + "\"" + "}";
            const formData = new FormData();
            formData.append("attachmentDTO", attachmentDto);
            formData.append("file", file, file.name);
            await axios.post(ApiUrl() + "attachment", formData).then(() => {
                setMessage(["Contract uploaded."]);
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


    return(
        <>
            <NavMenu/>
            <Modal open = {open} onClose = {handleClose}>
                <Box sx = {style}>
                    <Typography variant = "h3">Submit contract</Typography>
                    <input onChange={event => handleFileChange(event)} type="file" multiple ={false}/>
                    <Button onClick ={handleUpload}>Upload</Button>
                </Box>
            </Modal>
            <Container>
                <NavigationGroup id = {id} unallowedToClick={false} unallowedAttach={false}/>
                <Card>
                    <Typography variant = "h2">Contract details</Typography>
                    {
                        showMessage && <AlertMessage severity={severity} message={message} setShowMessage={setShowMessage}/>
                    }
                    <CardContent>
                        <Typography margin = "normal" variant = "h4">Publication release date and price</Typography>
                        <LocalizationProvider dateAdapter={AdapterDateFns}>
                            <DatePicker
                                disabled={disabled}
                                label="Publish date"
                                value={publishDate}
                                onChange={(newValue) => {
                                    setPublishDate(newValue);
                                }}
                                renderInput={(params) => <TextField {...params} />}
                            />
                        </LocalizationProvider>
                        <TextField type="number" disabled={disabled} margin = "normal" fullWidth label = "Publication price" value = {publicationPrice} onChange={event => setPublicationPrice(parseFloat(event.target.value))} InputLabelProps={{ shrink: publicationPrice ? true : false }}/>
                        <Typography margin = "normal" variant = "h4">Author compensation</Typography>
                        <TextField type="number" disabled={disabled} margin = "normal" fullWidth label = "Amount on signing contract" value = {amountOnSigningContract} onChange={event => setAmountOnSigningContract(parseFloat(event.target.value))} />
                        <TextField type="number" disabled={disabled} margin = "normal" fullWidth label = "Amount on completing manuscript" value = {amountOfCompletedManuscript} onChange={event => setAmountOfCompletedManuscript(parseFloat(event.target.value))} />
                        <TextField type="number" disabled={disabled} margin = "normal" fullWidth label = "Amount on initial publish" value = {amountOnInitialPublish} onChange={event => setAmountOnInitialPublish(parseFloat(event.target.value))} />
                        <TextField type="number" disabled={disabled} margin = "normal" fullWidth label = "Paid within months after publish" value = {withinMonthsAfterPublish} onChange={event => setWithinMonthsAfterPublish(parseFloat(event.target.value))}/>
                        <Typography margin = "normal" variant = "h4">First cover</Typography>
                        <TextField type="number" disabled={disabled} margin = "normal" fullWidth label = "First cover rate" value = {firstCoverRate} onChange={event => setFirstCoverRate(parseFloat(event.target.value))}/>
                        <TextField type="number" disabled={disabled} margin = "normal" fullWidth label = "First cover percent" value = {firstCoverPercent} onChange={event => setFirstCoverPercent(parseFloat(event.target.value))} />
                        <Typography margin = "normal" variant = "h4">Second cover</Typography>
                        <TextField type="number" disabled={disabled} margin = "normal" fullWidth label = "Second cover rate" value = {secondCoverRate} onChange={event => setSecondCoverRate(parseFloat(event.target.value))}/>
                        <TextField type="number" disabled={disabled} margin = "normal" fullWidth label = "Second cover percent" value = {secondCoverPercent} onChange={event => setSecondCoverPercent(parseFloat(event.target.value))} />
                        <Typography margin = "normal" variant = "h4">Last cover</Typography>
                        <TextField type="number" disabled={disabled} margin = "normal" fullWidth label = "Last cover rate" value = {lastCoverRate} onChange={event => setLastCoverRate(parseFloat(event.target.value))}/>
                        <TextField type="number" disabled={disabled} margin = "normal" fullWidth label = "Last cover percent" value={lastCoverPercent} onChange={event => setLastCoverPercent(parseFloat(event.target.value))} />
                        {
                            context.data?.role === "Publication Manager" && <Button onClick = {handleSave} variant="contained" color="success">Save contract details</Button>
                        }
                        {
                            context.data?.role === "Publication Manager" && contractId !== null ? <Button onClick = {handleGenerateContract} variant="contained" color="secondary">Generate contract</Button> : ""
                        }
                        {
                            context.data?.role === "Author" && contractId !== null ? <Button onClick = {handleOpen} variant="contained" color="success">Submit signed contract</Button> : ""
                        }
                        {
                            context.data?.role === "Publication Manager" && contractId !== null ? <Button onClick ={handleContractSigned} variant="contained" color="error">Set contract signed</Button> : ""
                        }
                    </CardContent>
                </Card>
                {
                    contract.contractId !== null && <Comments comments={contractComments} url ={"contract"} entityId={contract.contractId} setComments={setComments} commentator={commentator}/>
                }
            </Container>
        </>
    )
}

export default ContractPage