import {useParams} from "react-router-dom";
import {Button, Card, CardContent, Container, TextField, Typography} from "@mui/material";
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
        publicationId: null,
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
    const [publicationId, setPublicationId] = useState<number | null>(contract.publicationId)
    const [publicationPrice, setPublicationPrice] = useState<number>(contract.publicationPrice)
    const [publishDate, setPublishDate] = useState<Date | null>(contract.publishDate)
    const [secondCoverPercent, setSecondCoverPercent] = useState<number>(contract.secondCoverPercent)
    const [secondCoverRate, setSecondCoverRate] = useState<number>(contract.secondCoverRate)
    const [withinMonthsAfterPublish, setWithinMonthsAfterPublish] = useState<number>(contract.withinMonthsAfterPublish)
    const [contractComments, setComments] = useState<UserComment[]>([]);
    const [commentator, setCommentator] = useState<User>({
        email: "", firstName: "", id: null, lastName: "", role: "", username: ""
    })

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
    }

    const handleSave = () => {
        axios.post<Contract>(ApiUrl() + "contract", {
            publicationId: typeof id === "string" ? parseInt(id) : null,
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
        }).then(response => {handleStateChange(response)})
    }

    const handleContractSigned = () => {
        axios.post<Publication>(ApiUrl() + "publication/" + id + "/setContract")
    }

    const handleGenerateContract = () => {
        axios.get(ApiUrl() + "contract/" + id + "/downloadContract");
    }


    return(
        <>
            <Navbar/>
            <SideMenu/>
            <Container>
                <NavigationGroup id = {id}/>
                <Card>
                    <Typography variant = "h2">Contract details</Typography>
                    <CardContent>
                        <TextField margin = "normal" fullWidth label = "Publish date" value={publishDate}/>
                        <TextField margin = "normal" fullWidth label = "Publication price" value = {publicationPrice} onChange={event => setPublicationPrice(parseFloat(event.target.value))}/>
                        <TextField margin = "normal" fullWidth label = "Amount on signing contract" value = {amountOnSigningContract} onChange={event => setAmountOnSigningContract(parseFloat(event.target.value))} />
                        <TextField margin = "normal" fullWidth label = "Amount on completing manuscript" value = {amountOfCompletedManuscript} onChange={event => setAmountOfCompletedManuscript(parseFloat(event.target.value))} />
                        <TextField margin = "normal" fullWidth label = "Amount on initial publish" value = {amountOnInitialPublish} onChange={event => setAmountOnInitialPublish(parseFloat(event.target.value))} />
                        <TextField margin = "normal" fullWidth label = "Within months after publish" value = {withinMonthsAfterPublish} onChange={event => setWithinMonthsAfterPublish(parseFloat(event.target.value))}/>
                        <TextField margin = "normal" fullWidth label = "First cover rate" value = {firstCoverRate} onChange={event => setFirstCoverRate(parseFloat(event.target.value))}/>
                        <TextField margin = "normal" fullWidth label = "First cover percent" value = {firstCoverPercent} onChange={event => setFirstCoverPercent(parseFloat(event.target.value))} />
                        <TextField margin = "normal" fullWidth label = "Second cover rate" value = {secondCoverRate} onChange={event => setSecondCoverRate(parseFloat(event.target.value))}/>
                        <TextField margin = "normal" fullWidth label = "Second cover percent" value = {secondCoverPercent} onChange={event => setSecondCoverPercent(parseFloat(event.target.value))} />
                        <TextField margin = "normal" fullWidth label = "Last cover rate" value = {lastCoverRate} onChange={event => setLastCoverRate(parseFloat(event.target.value))}/>
                        <TextField margin = "normal" fullWidth label = "Last cover percent" value={lastCoverPercent} onChange={event => setLastCoverPercent(parseFloat(event.target.value))} />
                        <Button onClick = {handleSave}>Save contract details</Button>
                        {
                            contractId !== null ? <Button onClick = {handleGenerateContract}>Generate contract</Button> : ""
                        }
                        {
                            contractId !== null ? <Button>Submit signed contract</Button> : ""
                        }
                        {
                            contractId !== null ? <Button onClick ={handleContractSigned}>Set contract signed</Button> : ""
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