function main() {
   // Widget instantiation metadata...
   var widget = {
      id : "ConsoleTagManagement", 
      name : "Alfresco.ConsoleTagManagement",
      options : {
         pageSize: (args.pageSize != null) ? args.pageSize : "15"
      }
   };
   model.widgets = [widget];
}
main();