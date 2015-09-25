using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Controls;

namespace AtRaCon_Client.Pages
{
    public static class ArcPageHandler
    {
        private static readonly Dictionary<string,Page>  _pages=new Dictionary<string, Page>();

        public static void AddPage(Page page)
        {
            AddPage(page.GetType().Name,page);
        }

        public static void AddPage(string pageName, Page page)
        {
            _pages.Add(pageName,page);
        }

        public static Page GetPage(string pageName)
        {
            if (_pages.ContainsKey(pageName))
            {
            return _pages[pageName];
        }
            Console.WriteLine("No page: "+pageName);
            return null;
        }
    }
}
