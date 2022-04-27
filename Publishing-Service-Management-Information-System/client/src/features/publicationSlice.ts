import {createSlice, PayloadAction} from "@reduxjs/toolkit";

interface Publication {
    name: string;
}

export interface PublicationState {
    value: Publication[];
}

const initialState: PublicationState = {
    value: [],
}

export const publicationSlice = createSlice({
    name: "publication",
    initialState,
    reducers: {
        addPublication: (state, action: PayloadAction<Publication>) => {

        }
    }
})

export const {addPublication} = publicationSlice.actions;
export default publicationSlice.reducer;

