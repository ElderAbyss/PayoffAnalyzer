package analyzer.com.jodycaudill.payoffAnalyzer;

/**
 * Created by Jody_Admin on 11/7/2016.
 */
public class AboutModal {
    public static void display() {
        double widthOfhelp = 500;
        String help = "Payoff Analyzer\n" +
                "CSC 318 Final Project\n" +
                "Jody  Caudill  -  https://github.com/ElderAbyss/PayoffAnalyzer\n" +
                "\n" +
                "Copyright (c) 2016 Jody Caudill\n" +
                "Permission is hereby granted, free of charge, to any person obtaining a copy of this software " +
                "and associated documentation files (the “Software”), to deal in the Software without restriction, " +
                "including without limitation the rights to use, copy, modify, merge, publish, distribute, " +
                "sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is " +
                "furnished to do so, subject to the following conditions:\n" +
                "\n" +
                "The above copyright notice and this permission notice shall be included in all copies or substantial " +
                "portions of the Software.\n" +
                "THE SOFTWARE IS PROVIDED “AS IS”, WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT " +
                "LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. " +
                "IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER " +
                "LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN " +
                "CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.\n";

        String how = "";

        String title = "Payoff Analyzer About";

        String header1 = "Payoff Analyzer";
        String header2 = "";

        InformationModal.display(title,header1,help,header2,how ,widthOfhelp);
    }
}
