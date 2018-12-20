
Dinner Planner. By Sergiy Isakov. 12.12.17
Short description.

Simple run: Class utils.GuiItems.Main -> main method. Instance of gui.groceries.Groceries takes as a parameter a name of a directory in a project root,
            or a full path to directory outside of project.
            There are two options to run:
            One is groceries.makeGroceriesList(); method for minimal solution.
            The other is groceries.mkGroceriesServings(5); , takes an amount of servings as a parameter.

GUI:        Class gui.utils.MenuController -> run main method.
            It has a TabMenu - File(Open File, Open Directory, SaveFile, Save As...) and Help(About).
            "Open File" - you can open a single file.
            "Open Directory" - opens all files in chosen directory.
            Then in a left TextArea appears a list of recipes names.
            In a textField "Servings" you type in amount of servings, then push "Create List".
            Shopping list appears in a main TextArea. It can be cleaned out by pressing "Clear List".
            However, pushing "Create List" button before adding files, or with "0" servings will show a
            dialog with error.
            Shopping list can be saved -> "Save As...", and updated list can be saved to same file by "Save File"

Extensions: Servings and Merging identical ingredients(class gui.groceries.Groceries), Converting units(class gui.groceries.Converter),
            and GUI(gui.utils.MenuController, gui.View, gui.utils.MenuHelper)