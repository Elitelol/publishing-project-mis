import {useContext, useEffect, useState} from "react";
import Publication from "../models/Publication";
import {UserContext} from "../auth";
import axios from "axios";
import ApiUrl from "../config/api.config";

const DashboardPage = () => {

    const [context, setContext] = useContext(UserContext);
    const [publications, setPublications] = useState<Publication[]>([]);

    useEffect(() => {
        fetchPublications().then(res => {
            setPublications(res.data)
            console.log(publications)
        });
    }, [])

    const fetchPublications = async () => {
        let url: string
        let role = context.data?.role;
        let userId = context.data?.id
        if (role === "Author") {
            url = "publication/author/";
        } else if (role === "Publication Manager") {
            url = "publication/manager/";
        } else {
            url = "todo";
        }
        return await axios.get(ApiUrl() + url + userId);
    }

    return <>
    </>
}

export default DashboardPage