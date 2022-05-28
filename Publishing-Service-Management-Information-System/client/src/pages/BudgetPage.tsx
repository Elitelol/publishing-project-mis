import {AlertColor, Button, Card, CardContent, Container, TextField, Typography} from "@mui/material";
import NavigationGroup from "../components/NavigationGroup";
import {useParams} from "react-router-dom";
import React, {useContext, useEffect, useState} from "react";
import Budget from "../models/Budget";
import axios, {AxiosResponse} from "axios";
import ApiUrl from "../config/api.config";
import Contract from "../models/Contract";
import Comments from "../components/Comments";
import UserComment from "../models/UserComment";
import User from "../models/User";
import {UserContext} from "../auth";
import Navbar from "../components/Navbar";
import SideMenu from "../components/SideMenu";
import NavMenu from "../components/NavMenu";
import AlertMessage from "../components/AlertMessage";

const BudgetPage = () => {

    const {id} = useParams()
    const [context, setContext] = useContext(UserContext);

    useEffect(() => {
        try{
            axios.get<User>(ApiUrl() + "user/" + context.data?.id).then(response => setCommentator(response.data))
            axios.get<Contract>(ApiUrl() + "budget/" + id).then(response => handleStateChange(response));
        } catch (error) {

        }
    },[])

    const [budget, setBudget] = useState<Budget>({
        advertisingCost: 0,
        budgetId: null,
        colourPrintingRate: 0,
        comments: [],
        copyEditingRate: 0,
        copyMailingCost: 0,
        coverDesignQuantity: 0,
        coverDesignRate: 0,
        deliveryToStorageRate: 0,
        interiorLayoutRate: 0,
        numberOfCopies: 0,
        printingRate: 0,
        proofReadingRate: 0,
        publicationId: "",
        purchaseOfPhotosQuantity: 0,
        purchaseOfPhotosRate: 0,
        pageNumber: 0
    })

    const [budgetId, setBudgetId] = useState<number | null>(budget.budgetId);
    const [pageNumber, setPageNumber] = useState<number>(budget.pageNumber);
    const [numberOfCopies, setNumberOfCopies] = useState<number>(budget.numberOfCopies);
    const [copyEditingRate, setCopyEditingRate] = useState<number>(budget.copyEditingRate);
    const [proofReadingRate, setProofReadingRate] = useState<number>(budget.proofReadingRate);
    const [purchaseOfPhotosRate, setPurchaseOfPhotosRate] = useState<number>(budget.purchaseOfPhotosRate);
    const [purchaseOfPhotosQuantity, setPurchaseOfPhotosQuantity] = useState<number>(budget.purchaseOfPhotosQuantity);
    const [coverDesignRate, setCoverDesignRate] = useState<number>(budget.coverDesignRate);
    const [coverDesignQuantity, setCoverDesignQuantity] = useState<number>(budget.coverDesignQuantity);
    const [interiorLayoutRate, setInteriorLayoutRate] = useState<number>(budget.interiorLayoutRate);
    const [printingRate, setPrintingRate] = useState<number>(budget.printingRate);
    const [colourPrintingRate, setColourPrintingRate] = useState<number>(budget.colourPrintingRate);
    const [deliveryToStorageRate, setDeliveryToStorageRate] = useState<number>(budget.deliveryToStorageRate);
    const [advertisingCost, setAdvertisingCost] = useState<number>(budget.advertisingCost);
    const [copyMailingCost, setCopyMailingCost] = useState<number>(budget.copyMailingCost);
    const [budgetComments, setComments] = useState<UserComment[]>([]);
    const [commentator, setCommentator] = useState<User>({
        email: "", firstName: "", id: null, lastName: "", role: "", username: ""
    })
    const [disabled, setDisabled] = useState<boolean>(true);
    const[showMessage, setShowMessage] = useState<boolean>(false);
    const [message, setMessage] = useState<string[]>([]);
    const [severity, setSeverity] = useState<AlertColor | undefined>("success");

    const handleStateChange = (response: AxiosResponse<any, any>) => {
        setBudget(response.data)
        setBudgetId(response.data.budgetId)
        setPageNumber(response.data.pageNumber)
        setNumberOfCopies(response.data.numberOfCopies)
        setCopyEditingRate(response.data.copyEditingRate)
        setProofReadingRate(response.data.proofReadingRate)
        setPurchaseOfPhotosRate(response.data.purchaseOfPhotosRate)
        setPurchaseOfPhotosQuantity(response.data.purchaseOfPhotosQuantity)
        setCoverDesignRate(response.data.coverDesignRate)
        setCoverDesignQuantity(response.data.coverDesignQuantity)
        setInteriorLayoutRate(response.data.interiorLayoutRate)
        setPrintingRate(response.data.printingRate)
        setColourPrintingRate(response.data.colourPrintingRate)
        setDeliveryToStorageRate(response.data.deliveryToStorageRate)
        setAdvertisingCost(response.data.advertisingCost)
        setCopyMailingCost(response.data.copyMailingCost)
        setComments(response.data.comments)
        setDisabled(context.data?.role !== "Publication Manager");
    }

    const handleSave = async () => {
        await axios.post<Budget>(ApiUrl() + "budget",{
            publicationId: id,
            budgetId,
            pageNumber,
            numberOfCopies,
            copyEditingRate,
            proofReadingRate,
            purchaseOfPhotosRate,
            purchaseOfPhotosQuantity,
            coverDesignRate,
            coverDesignQuantity,
            interiorLayoutRate,
            printingRate,
            colourPrintingRate,
            deliveryToStorageRate,
            advertisingCost,
            copyMailingCost
        }).then(response => {
            handleStateChange(response)
            setMessage(["Budget saved."]);
            setShowMessage(true);
            setSeverity("success");
        }).catch(error => {
            setMessage(error.response.data.message)
            setShowMessage(true);
            setSeverity("error");
        })
    }

    const handleGetReport = () => {
        axios.get(ApiUrl() + "budget/" + budgetId + "/downloadBudget", {responseType: "blob"}).then(response => {
            const url = window.URL.createObjectURL(new Blob([response.data]));
            const link = document.createElement('a');
            link.href = url;
            link.download = "budget_" + id + ".pdf"
            link.click()
        })
    }

   return(
       <>
           <NavMenu/>
           <Container>
               <NavigationGroup id = {id} unallowedToClick={false} unallowedAttach={false}/>
               <Card>
                   <Typography variant = "h2">Publishing budget details</Typography>
                   {
                       showMessage && <AlertMessage severity={severity} message={message} setShowMessage={setShowMessage}/>
                   }
                   <CardContent>
                       <Typography margin = "normal" variant = "h4">Publication information</Typography>
                       <TextField type="number" disabled={disabled} margin = "normal" fullWidth label = "Number of pages" value={pageNumber} onChange={event => setPageNumber(parseInt(event.target.value))} InputLabelProps={{ shrink: pageNumber ? true : false }}/>
                       <TextField type="number" disabled={disabled} margin = "normal" fullWidth label = "Number of copies" value={numberOfCopies} onChange={event => setNumberOfCopies(parseInt(event.target.value))}/>
                       <Typography margin = "normal" variant = "h4">Editorial</Typography>
                       <TextField type="number" disabled={disabled} margin = "normal" fullWidth label = "Copy editing rate" value={copyEditingRate} onChange={event => setCopyEditingRate(parseFloat(event.target.value))}/>
                       <TextField type="number" disabled={disabled} margin = "normal" fullWidth label = "Proof reading rate" value={proofReadingRate} onChange={event => setProofReadingRate(parseFloat(event.target.value))}/>
                       <TextField type="number" disabled={disabled} margin = "normal" fullWidth label = "Purchase of photos rate" value={purchaseOfPhotosRate} onChange={event => setPurchaseOfPhotosRate(parseFloat(event.target.value))}/>
                       <TextField type="number" disabled={disabled} margin = "normal" fullWidth label = "Purchase of photos quantity" value={purchaseOfPhotosQuantity} onChange={event => setPurchaseOfPhotosQuantity(parseFloat(event.target.value))}/>
                       <TextField type="number" disabled={disabled} margin = "normal" fullWidth label = "Cover design rate" value={coverDesignRate} onChange={event => setCoverDesignRate(parseFloat(event.target.value))}/>
                       <TextField type="number" disabled={disabled} margin = "normal" fullWidth label = "Cover design quantity" value={coverDesignQuantity} onChange={event => setCoverDesignQuantity(parseFloat(event.target.value))}/>
                       <TextField type="number" disabled={disabled} margin = "normal" fullWidth label = "Interior layout rate" value={interiorLayoutRate} onChange={event => setInteriorLayoutRate(parseFloat(event.target.value))}/>
                       <Typography margin = "normal" variant = "h4">Printing preparation</Typography>
                       <TextField type="number" disabled={disabled} margin = "normal" fullWidth label = "Printing rate" value={printingRate} onChange={event => setPrintingRate(parseFloat(event.target.value))}/>
                       <TextField type="number" disabled={disabled} margin = "normal" fullWidth label = "Colour printing rate" value={colourPrintingRate} onChange={event => setColourPrintingRate(parseFloat(event.target.value))}/>
                       <TextField type="number" disabled={disabled} margin = "normal" fullWidth label = "Delivery to storage rate" value={deliveryToStorageRate} onChange={event => setDeliveryToStorageRate(parseFloat(event.target.value))}/>
                       <Typography margin = "normal" variant = "h4">Communication</Typography>
                       <TextField type="number" disabled={disabled} margin = "normal" fullWidth label = "Advertising cost" value={advertisingCost} onChange={event => setAdvertisingCost(parseFloat(event.target.value))}/>
                       <TextField type="number" disabled={disabled} margin = "normal" fullWidth label = "Copy mailing cost" value={copyMailingCost} onChange={event => setCopyMailingCost(parseFloat(event.target.value))}/>
                       {
                           context.data?.role === "Publication Manager" && <Button onClick = {handleSave} variant="contained" color="success">Save details</Button>
                       }
                       {
                           budgetId !== null ? <Button onClick = {handleGetReport} variant="contained" color="secondary">Get budget report</Button> : ""
                       }
                   </CardContent>
               </Card>
               {
                   budget.budgetId !== null && <Comments comments={budgetComments} url ={"budget"} entityId={budget.budgetId} setComments={setComments} commentator={commentator}/>
               }
           </Container>
       </>
   )
}

export default BudgetPage