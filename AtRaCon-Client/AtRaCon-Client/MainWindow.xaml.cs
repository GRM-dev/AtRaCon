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
        
        public MainWindow()
        {
            _instance = this;
            InitializeComponent();
            ArcPageHandler.AddPage(new LoginPage());
            ArcPageHandler.AddPage(new MainPage());
            var client = new AtRaConClient(this);
            MainFrame.Navigate(ArcPageHandler.GetPage("LoginPage"));

        }

      public void NavigateTo(string pageName)
      {
          var page = ArcPageHandler.GetPage(pageName);
            if (page !=null)
            {
                MainFrame.Navigate(page);
            }
        }

        public static MainWindow Instance => _instance;
    }
}
