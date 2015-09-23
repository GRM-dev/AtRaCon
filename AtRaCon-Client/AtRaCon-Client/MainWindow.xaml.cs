using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows;
using System.Windows.Controls;
using System.Windows.Data;
using System.Windows.Documents;
using System.Windows.Input;
using System.Windows.Media;
using System.Windows.Media.Imaging;
using System.Windows.Navigation;
using System.Windows.Shapes;
using AtRaCon_Client.Core;
using AtRaCon_Client.Pages;

namespace AtRaCon_Client
{
    /// <summary>
    /// Interaction logic for MainWindow.xaml
    /// </summary>
    public partial class MainWindow : Window
    {
        private static MainWindow _instance;
        private LoginPage _loginPage;
        private MainPage _mainPage;
        public MainWindow()
        {
            _instance = this;
            InitializeComponent();
            _loginPage = new LoginPage();
            _mainPage = new MainPage();
            var client = new AtRaConClient();
            MainFrame.Navigate(_loginPage);

        }

      public void NavigateTo(string page)
        {
            if (page == "main")
            {
                MainFrame.Navigate(_mainPage);
            }
        }

        public static MainWindow Instance => _instance;
    }
}
