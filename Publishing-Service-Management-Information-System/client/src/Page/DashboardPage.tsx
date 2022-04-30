import {useContext, useEffect, useState} from "react";
import Publication from "../models/Publication";
import {UserContext} from "../auth";
import axios from "axios";
import ApiUrl from "../config/api.config";
import {ProgressBar, Table} from "react-bootstrap";

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
        <div>
            My Works;
            <Table striped hover size={"50sm"} >
                <thead>
                <tr>
                    <th> Id </th>
                    <th> Publication name </th>
                    <th> Publication type </th>
                    <th> Genre</th>
                    <th> Author </th>
                    <th>Progress status</th>
                    <th>Progress</th>
                    <th>Manager</th>
                </tr>
                </thead>
                {
                    publications.map(publication => {
                        return <tbody>
                        <tr>
                            <td>{publication.publicationId}</td>
                            <td>{publication.name}</td>
                            <td>{publication.publicationType}</td>
                            <td>{publication.genre}</td>
                            <td>{publication.authorId}</td>
                            <td>{publication.progressStatus}</td>
                            <td>
                                <ProgressBar variant={"success"} now={45} label = {`${45}%`} />
                            </td>
                            <td>{publication.managerId}</td>
                        </tr>
                        </tbody>
                    })
                }
            </Table>
        </div>
    </>
}

export default DashboardPage