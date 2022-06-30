using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Mvc;
using Prometheus;

namespace API.Controllers
{
    [ApiController]
    [Route("[controller]")]
    public class RockPaperScissorController
    {
        private static readonly string[] RockPaperScissorArray = new[] { "Rock", "Paper", "Scissor" };
        private static readonly Counter RockCount = Metrics.CreateCounter("net_core_rock_counts", "Number of rock rolls on the .NET Core instance");
        private static readonly Counter PaperCount = Metrics.CreateCounter("net_core_paper_counts", "Number of paper rolls on the .NET Core instance");
        private static readonly Counter ScissorCount = Metrics.CreateCounter("net_core_scissor_counts", "Number of scissor rolls on the .NET Core instance");

        [HttpGet]
        public string RockPaperScissor()
        {
            var random = new Random();
            int index = random.Next(0, RockPaperScissorArray.Length);
            string result = RockPaperScissorArray[index];

            if (result.Equals("Rock"))
                RockCount.Inc();
            else if (result.Equals("Paper"))
                PaperCount.Inc();
            else
                ScissorCount.Inc();

            return result;
        }
    }
}
