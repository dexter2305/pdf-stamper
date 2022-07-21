# PDF Stamper

Tool that takes a PDF template and data file in the form of csv file to stamp copies of template for every tuple in the csv. 

```
( (template ppt => template pdf) + csv) => zip { pdf }*
```
## How to create a template?

- Use PPT *Design mode*.
- Use textbox for placeholders. 
- Textboxes can have names. They are called *field names*. 
- Field names are case-sensitive. Use meaningful names for every textbox used, you will be using field names later on. *Example username, ideaname*. These fields will be used as headers in the data(csv) file. 
- Optionally, set one of the field names as the property of the PPT. *Example. username*. When this set, value from the csv file belonging to the set field name is used as name of generated pdf file. 
- Export as PDF. 
### Textbox aesthetics 

- Border to be set as *without frame*, to remain transparent
- Tab stop should be *disabled*
- Background color to match the slide bg, if not already

## How to create data file? 
- Data file is a CSV formatted file with a header
- Header in the CSV file should match the field names

## How to stamp pdf? 
- Use the given url
- Upload pdf & csv and generate.

## What is not done yet? 
- No verification of the fieldnames in template pdf is made against the headers in csv
- Malformed records are not reported back in the UI.


*Might work with word, excel as well. Any tool that can desgin forms and export as pdf should be compatible. I tested only with ppt*.

