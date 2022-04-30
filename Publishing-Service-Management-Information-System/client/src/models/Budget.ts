import UserComment from "./UserComment";

export default interface Budget {
    publicationId: number
    budgetId: number
    numberOfCopies: number
    copyEditingRate: number
    proofReadingRate: number
    purchaseOfPhotosRate: number
    purchaseOfPhotosQuantity: number
    coverDesignRate: number
    coverDesignQuantity: number
    interiorLayoutRate: number
    printingRate: number
    colourPrintingRate: number
    deliveryToStorageRate: number
    advertisingCost: number
    copyMailingCost: number
    comments: UserComment[]
}