﻿using System;
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

namespace AtRaCon_Client.Pages
{
    /// <summary>
    /// Interaction logic for MainPage.xaml
    /// </summary>
    public partial class MainPage : Page
    {
        public MainPage()
        {
            InitializeComponent();
        }

        private void DevList_Expander_Expand(object sender, RoutedEventArgs e)
        {
            Console.WriteLine("Refreshing List of Raspberry Pi Devices");

        }

        private void RegList_Expander_Expand(object sender, RoutedEventArgs e)
        {

        }
    }
}
