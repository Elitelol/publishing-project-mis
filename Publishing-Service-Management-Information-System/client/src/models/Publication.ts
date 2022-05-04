import Attachment from "./Attachment";
import Contract from "./Contract";
import Budget from "./Budget";
import Task from "./Task";
import User from "./User";

export default interface Publication {
    publicationId: number | null
    authors: User[]
    name: string
    publicationType: string
    progressStatus: string
    progressPercent: number
    rejectionReason: string
    isbn: string
    pageNumber: number | null
    language: string
    genre: string
    price: number | null
    publishDate: Date | null
    manager: User | null
    attachments: Attachment[]
    contract: Contract | null
    budget: Budget | null
    tasks: Task[]
}